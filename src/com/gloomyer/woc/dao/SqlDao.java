package com.gloomyer.woc.dao;

import com.gloomyer.woc.model.ArticleModel;
import com.gloomyer.woc.model.CategoryModel;
import com.gloomyer.woc.model.FileModel;
import com.gloomyer.woc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gloomy on 2016/10/27.
 */
public class SqlDao {

    /**
     * 用于记录当前缓存数据是否为最新的
     */
    public static boolean isNew = false;
    /**
     * 缓存的内存数据
     */
    private static List<CategoryModel> cache;

    private static SqlDao instance;

    public static SqlDao getInstance() {
        synchronized (SqlDao.class) {
            if (instance == null)
                instance = new SqlDao();
        }
        return instance;
    }

    private SqlDao() {

    }


    /**
     * 获取所有类别
     */
    public List<CategoryModel> getCateGorys() {
        List<CategoryModel> list = new ArrayList<>();
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("select * from t_category;");
                rs = pst.executeQuery();
                while (rs.next()) {
                    CategoryModel category = new CategoryModel();
                    category.setCategoryId(rs.getInt(1));
                    category.setCategoryName(rs.getString(2));
                    list.add(category);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, rs);
        return list;
    }

    public void getArticles(CategoryModel category) {
        List<ArticleModel> list = new ArrayList<>();
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("select * from t_article where categoryId = " + category.getCategoryId() + ";");
                rs = pst.executeQuery();
                while (rs.next()) {
                    ArticleModel article = new ArticleModel();
                    article.setaId(rs.getInt(1));
                    article.setTitle(rs.getString(3));
                    article.setDesc(rs.getString(4));
                    article.setImg(rs.getString(5));
                    article.setUrl(rs.getString(6));
                    list.add(article);
                }
                category.setArticles(list);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, rs);
    }

    public List<CategoryModel> getAllInfo() {
        if (cache != null && isNew)
            return cache;

        List<CategoryModel> cateGorys = getCateGorys();
        for (CategoryModel cateGory : cateGorys) {
            getArticles(cateGory);
        }
        cache = cateGorys;
        isNew = true;
        return cache;
    }

    public boolean addCategory(String cName) {
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        boolean result = false;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("INSERT INTO t_category(cName) VALUES(?);");
                pst.setString(1, cName);

                int line = pst.executeUpdate();
                if (line > 0)
                    result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, null);
        return result;
    }

    public boolean deleteCategory(int cId) {
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        boolean result = false;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("DELETE FROM t_category WHERE cId = ?;");
                pst.setInt(1, cId);

                int line = pst.executeUpdate();
                if (line > 0)
                    result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, null);
        return result;
    }

    public boolean editCategory(int cId, String cName) {
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        boolean result = false;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("UPDATE t_category set cName = ? WHERE cId = ?;");
                pst.setString(1, cName);
                pst.setInt(2, cId);
                int line = pst.executeUpdate();
                if (line > 0)
                    result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, null);
        return result;
    }

    public boolean deleteArticle(int aId) {
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        boolean result = false;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("DELETE FROM t_article WHERE id = ?;");
                pst.setInt(1, aId);

                int line = pst.executeUpdate();
                if (line > 0)
                    result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, null);
        return result;
    }


    public boolean addFileInfoToDatabase(FileModel file) {
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        boolean result = false;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("INSERT INTO t_file(uuid,name,file) VALUES (?, ?, ?);");
                pst.setString(1, file.getUuid());
                pst.setString(2, file.getFileName());
                pst.setString(3, file.getFilePath());
                int line = pst.executeUpdate();
                if (line > 0)
                    result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, null);
        return result;
    }

    public boolean addArticle(int cId, String title, String desc, String img, String uuid) {

        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        boolean result = false;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("INSERT INTO t_article(categoryId,title,c_desc,img,file) VALUES (?, ?, ?, ?, ?);");
                pst.setInt(1, cId);
                pst.setString(2, title);
                pst.setString(3, desc);
                pst.setString(4, img);
                pst.setString(5, uuid);
                int line = pst.executeUpdate();
                if (line > 0)
                    result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, null);
        return result;
    }

    public boolean deleteFile(String uuid) {
        Connection conn = JDBCUtils.getConn();
        PreparedStatement pst = null;
        boolean result = false;
        if (conn != null) {
            try {
                pst = conn.prepareStatement("DELETE FROM t_file WHERE uuid = ?;");
                pst.setString(1, uuid);
                int line = pst.executeUpdate();
                if (line > 0)
                    result = true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        JDBCUtils.close(conn, pst, null);
        return result;
    }
}
