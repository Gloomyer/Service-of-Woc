package com.gloomyer.woc.servlet;

import com.gloomyer.woc.dao.SqlDao;
import com.gloomyer.woc.utils.JsonUtils;
import com.gloomyer.woc.utils.PasswordUtils;
import com.gloomyer.woc.utils.TextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Woc后端服务
 */
public class WocServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=UTF-8");

        JsonUtils.wrtiteJson(response, 201, false, "请使用POST方式调用本接口!");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=UTF-8");

        String methodStr = request.getParameter("method");
        if (TextUtils.isEmpty(methodStr))
            methodStr = request.getHeader("method");
        if (!TextUtils.isEmpty(methodStr)) {
            try {
                Class<? extends WocServlet> clazz = getClass();
                Method method = clazz.getDeclaredMethod(methodStr, HttpServletRequest.class, HttpServletResponse.class);
                method.invoke(this, request, response);
            } catch (Exception e) {
                JsonUtils.wrtiteJson(response, 202, false, "请求方法不存在!");
            }
        } else {
            JsonUtils.wrtiteJson(response, 201, false, "需要指定请求方法!");
        }
    }

    /**
     * 获取首页的索引
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonUtils.wrtiteJson(response, 200, true, "查询成功", SqlDao.getInstance().getAllInfo());
    }

    /**
     * 添加分类
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd = request.getParameter("pwd");
        String cName = request.getParameter("cName");
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(cName)) {
            JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            return;
        }

        if (PasswordUtils.isExactness(pwd)) {
            //正确
            boolean result = SqlDao.getInstance().addCategory(cName);
            JsonUtils.wrtiteJson(response, result ? 200 : 203, result, result ? "添加分类成功!" : "分类添加失败!");
            if (result)
                SqlDao.isNew = false;
        } else {
            //密码错误
            JsonUtils.wrtiteJson(response, 202, false, "管理密码不正确!");
        }
    }

    /**
     * 删除分类
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd = request.getParameter("pwd");
        String cId = request.getParameter("cId");
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(cId)) {
            JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            return;
        }

        if (PasswordUtils.isExactness(pwd)) {
            //正确
            try {
                boolean result = SqlDao.getInstance().deleteCategory(Integer.parseInt(cId));
                JsonUtils.wrtiteJson(response, result ? 200 : 203, result, result ? "删除分类成功!" : "分类删除失败!");
                if (result)
                    SqlDao.isNew = false;
            } catch (Exception e) {
                JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            }
        } else {
            //密码错误
            JsonUtils.wrtiteJson(response, 202, false, "管理密码不正确!");
        }
    }

    /**
     * 编辑分类
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void editCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd = request.getParameter("pwd");
        String cId = request.getParameter("cId");
        String cName = request.getParameter("cName");
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(cId)) {
            JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            return;
        }

        if (PasswordUtils.isExactness(pwd)) {
            //正确
            try {
                boolean result = SqlDao.getInstance().editCategory(Integer.parseInt(cId), cName);
                JsonUtils.wrtiteJson(response, result ? 200 : 203, result, result ? "编辑分类成功!" : "分类编辑失败!");
                if (result)
                    SqlDao.isNew = false;
            } catch (Exception e) {
                JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            }
        } else {
            //密码错误
            JsonUtils.wrtiteJson(response, 202, false, "管理密码不正确!");
        }
    }


    /**
     * 删除文章
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void deleteArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd = request.getParameter("pwd");
        String cId = request.getParameter("aId");
        if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(cId)) {
            JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            return;
        }

        if (PasswordUtils.isExactness(pwd)) {
            //正确
            try {
                boolean result = SqlDao.getInstance().deleteArticle(Integer.parseInt(cId));
                JsonUtils.wrtiteJson(response, result ? 200 : 203, result, result ? "删除文章成功!" : "分类文章失败!");
                if (result) {
                    SqlDao.isNew = false;
                    //TODO 删除文章md文件
                }
            } catch (Exception e) {
                JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            }
        } else {
            //密码错误
            JsonUtils.wrtiteJson(response, 202, false, "管理密码不正确!");
        }
    }


}
