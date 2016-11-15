<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Woc后台操作页</title>
    <script language="javascript">

        var SERVER_URL = "http://localhost:8080/servlet/WocServlet"

        function getXMLHttp() {
            var xmlhttp;
            if (window.XMLHttpRequest) {
                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            } else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            return xmlhttp;
        }

        /**
         * 判断字符串是否为空
         */
        function isEmpty(str) {
            if (str !== null && str !== undefined && str !== '') {
                return false;
            }
            return true;
        }

        /**
         * 获取当前输入的密码对象，包含是否为空属性
         */
        function getPwd() {
            var resp = {
                success: false,
                pwd: ""
            };
            var pwd = document.getElementById("pwd");
            var input = pwd.value;
            if (!isEmpty(input)) {
                resp.success = true;
                resp.pwd = input;
            }
            return resp;
        }

        /**
         * 删除分类
         */
        function deleteCategory() {
            var cSelect = document.getElementById("cSelect");
            for (i = 0; i < cSelect.length; i++) {//下拉框的长度就是它的选项数.
                if (cSelect[i].selected == true) {
                    var text = cSelect[i].text;//获取当前选择项的文本.
                    if (confirm("你确认要删除 \"" + text + "\" 嘛?\n这将可能会导致该分类下的所有文章都被删除!")) {
                        var pwd = getPwd();
                        if (!pwd.success) {
                            alert("请输入密码!");
                            return;
                        }
                        var http = getXMLHttp();
                        http.open("POST", SERVER_URL + "?method=deleteCategory&cId="
                                + cSelect.value
                                + "&pwd="
                                + pwd.pwd
                                , true);
                        http.onreadystatechange = function () {
                            if (http.readyState == 4 && http.status == 200) {
                                var resp = eval('(' + http.responseText + ')')
                                if (resp.success) {
                                    //删除成功，重新拉去分类列表
                                    getCategorys();
                                } else {
                                    //删除失败提示用户
                                    alert(resp.message);
                                }
                            }
                        };
                        http.send();
                    }
                }
            }
        }

        /**
         * 添加分类
         */
        function addCategory() {
            var pwd = getPwd();
            if (!pwd.success) {
                alert("请输入密码");
                return
            }
            var cName = document.getElementById("cName").value;
            if (isEmpty(cName)) {
                alert("请输入分类名称")
                return;
            }

            var http = getXMLHttp();
            http.open("POST", SERVER_URL + "?method=addCategory&cName="
                    + cName
                    + "&pwd="
                    + pwd.pwd
                    , true);
            http.onreadystatechange = function () {
                if (http.readyState == 4 && http.status == 200) {
                    var resp = eval('(' + http.responseText + ')')
                    if (resp.success) {
                        //添加成功，重新拉去分类列表
                        document.getElementById("cName").value = "";
                        getCategorys();
                        alert("添加成功!");
                    } else {
                        //添加失败提示用户
                        alert(resp.message);
                    }
                }
            };
            http.send();
        }

        /**
         * 编辑分类
         */
        function editCategory() {
            var pwd = getPwd();
            if (!pwd.success) {
                alert("请输入密码");
                return
            }

            var cSelect = document.getElementById("cSelect");

            for (i = 0; i < cSelect.length; i++) {
                if (cSelect[i].selected == true) {
                    var text = cSelect[i].text;//获取当前选择项的文本.
                    var cName = prompt("请输入新的分类名称", text);
                    if (cName) {
                        var http = getXMLHttp();
                        http.open("POST", SERVER_URL
                                + "?method=editCategory"
                                + "&pwd=" + pwd.pwd
                                + "&cId=" + cSelect.value
                                + "&cName=" + cName
                                , true);
                        http.onreadystatechange = function () {
                            if (http.readyState == 4 && http.status == 200) {
                                var resp = eval('(' + http.responseText + ')')
                                if (resp.success) {
                                    //添加成功，重新拉去分类列表
                                    getCategorys();
                                    alert("修改成功!");
                                } else {
                                    //添加失败提示用户
                                    alert(resp.message);
                                }
                            }
                        }

                        http.send();
                    }
                }
            }
        }

        /**
         * 获取所有分类并显示
         */
        function getCategorys() {
            var http = getXMLHttp();
            http.open("POST", SERVER_URL + "?method=getCategorys", true);
            http.onreadystatechange = function () {
                if (http.readyState == 4 && http.status == 200) {
                    var categorys = eval('(' + http.responseText + ')')
                    if (categorys.success) {
                        //查询成功
                        var list = categorys.result;
                        var cDiv = document.getElementById("category");
                        if (list != undefined && list.length > 0) {
                            var html = "<select id='cSelect'>";
                            for (var i = 0; i < list.length; i++) {
                                //显示到document中去
                                html += "<option value='"
                                        + list[i].categoryId + "'>"
                                        + list[i].categoryName
                                        + "</option>";

                            }
                            html += "</select>"
                                    + " <input type='button' value='删除' onclick='deleteCategory();'/>"
                                    + " <input type='button' value='编辑' onclick='editCategory();' />";
                            cDiv.innerHTML = html;
                        } else {
                            cDiv.innerHTML = "<font color='red'>暂无分类</font>";
                        }
                    }
                }
            }
            http.send();
        }

        window.onload = function () {
            getCategorys();
        };
    </script>
</head>
<body>
<h1>Woc Web 操作端</h1>
<h5>操作密码:</h5>&nbsp;&nbsp;<input id="pwd" type="text"/><br/><br/>
<hr/>
<h5>分类管理</h5><br/>
<div>
    <font size="2">添加分类</font>
    <input id="cName" value="" type="text"/>
    <input value="确定" type="button" onclick="addCategory();"/>
</div>
<br/>
<div id="category"></div>
<hr/>
</body>
</html>
