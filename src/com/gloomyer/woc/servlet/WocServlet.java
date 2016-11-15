package com.gloomyer.woc.servlet;

import com.gloomyer.woc.dao.SqlDao;
import com.gloomyer.woc.model.CategoryModel;
import com.gloomyer.woc.model.FileModel;
import com.gloomyer.woc.utils.JsonUtils;
import com.gloomyer.woc.utils.PasswordUtils;
import com.gloomyer.woc.utils.TextUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * 查询分类
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getCategorys(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CategoryModel> categoryList = SqlDao.getInstance().getCateGorys();
        JsonUtils.wrtiteJson(response, 200, true, "查询成功", categoryList);
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
                }
            } catch (Exception e) {
                JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            }
        } else {
            //密码错误
            JsonUtils.wrtiteJson(response, 202, false, "管理密码不正确!");
        }
    }


    /**
     * 添加文章
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addArticle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pwd = request.getParameter("pwd");
        String title = request.getParameter("title");//文章标题
        String desc = request.getParameter("desc");//文章描述
        String img = request.getParameter("img");//文章图片地

        int cId = -1;
        try {
            cId = Integer.parseInt(request.getParameter("cId"));
        } catch (Exception e) {
            cId = -1;
        }

        if (TextUtils.isEmpty(pwd)
                || TextUtils.isEmpty(title)
                || TextUtils.isEmpty(desc)
                || TextUtils.isEmpty(img)
                || cId == -1) {
            JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
            return;
        }


        if (!PasswordUtils.isExactness(pwd)) {
            JsonUtils.wrtiteJson(response, 202, false, "管理密码不正确!");
            return;
        }

        // 创建硬盘文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 创建Servlet 文件上传项
        ServletFileUpload uploadFile = new ServletFileUpload(factory);
        // 全局化filePath,方便上传失败后的操作
        File filePath = null;
        // 判断上传的是否是 multipart/form-data 类型数据
        if (uploadFile.isMultipartContent(request)) {
            try {
                // 获取到上传的文件map集合
                Map<String, List<FileItem>> fileMap = uploadFile.parseParameterMap(request);
                // 获取file指向的文件
                List<FileItem> fileList = fileMap.get("mdFile");
                if (fileList == null || fileList.size() < 1) { //没有上传文件
                    JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
                    return;
                }
                FileItem fileItem = fileList.get(0);

                // 创建一个uuid,作为该文件的唯一标示
                String uuid = UUID.randomUUID().toString();
                // 获取上传资源的文件名
                String fileName = fileItem.getName();
                // 根据文件名获取hashCode
                String hash = Math.abs(fileName.hashCode()) + "";
                // 获取资源存放路径,并且进行hash打散
                File uploadDir = new File(getServletContext().getRealPath("WEB-INF") + "/files/");
                uploadDir = new File(uploadDir, "/" + hash.charAt(0) + "/" + hash.charAt(1) + "/");
                // 如果资源存放路径不存在,就创建资源路径
                if (!uploadDir.exists())
                    uploadDir.mkdirs();
                // 设置要写出的完整路径(加上uuid避免文件重复问题)
                filePath = new File(uploadDir, uuid + "_" + fileName);
                // 获取输入流
                InputStream is = fileItem.getInputStream();
                // 写出文件
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                // 释放资源
                is.close();
                bos.flush();
                bos.close();
                // 封装成 FileInfo对象,存入数据库中
                FileModel file = new FileModel(uuid, fileName, filePath.getAbsolutePath());
                boolean result = SqlDao.getInstance().addFileInfoToDatabase(file);
                if (result) {
                    // 添加成功
                    if (result) {
                        result = SqlDao.getInstance().addArticle(cId, title, desc, img, uuid);
                        if (result) {
                            //如果成功
                            JsonUtils.wrtiteJson(response, 200, true, "文章发布成功!");
                            SqlDao.isNew = false;
                            return;
                        } else {
                            SqlDao.getInstance().deleteFile(uuid);
                        }
                    }
                }
                //如果成功不会跑到这里
                JsonUtils.wrtiteJson(response, 203, false, "文件上传失败!");
                //如果文件已经存在，需要删除
                if (filePath != null && filePath.exists())
                    filePath.delete();
            } catch (Exception e) {
                // 如果出现异常,那么就表示上传失败,那么判断上传的文件是否已经生成,如果已经生成,就删除他
                JsonUtils.wrtiteJson(response, 203, false, "文件上传失败!");
                if (filePath != null && filePath.exists())
                    filePath.delete();
                e.printStackTrace();
            }
        } else {
            JsonUtils.wrtiteJson(response, 201, false, "参数不正确!");
        }
    }

}
