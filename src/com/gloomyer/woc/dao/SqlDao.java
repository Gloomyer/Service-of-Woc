package com.gloomyer.woc.dao;

import com.gloomyer.woc.model.ArticleModel;
import com.gloomyer.woc.model.CategoryModel;
import com.gloomyer.woc.model.FileModel;
import com.gloomyer.woc.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

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
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSorce());
        List<CategoryModel> list = null;
        try {
            list = qr.query("select * from t_category;", new ResultSetHandler<List<CategoryModel>>() {

                @Override
                public List<CategoryModel> handle(ResultSet rs) throws SQLException {

                    List<CategoryModel> list = new ArrayList<>();
                    while (rs.next()) {

                        CategoryModel info = new CategoryModel();
                        info.setCategoryId(rs.getInt(1));
                        info.setCategoryName(rs.getString(2));

                        list.add(info);
                    }

                    return list;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void getArticles(CategoryModel category) {

        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSorce());
        List<ArticleModel> list = null;
        try {
            list = qr.query("select * from t_article where categoryId = ?", new ResultSetHandler<List<ArticleModel>>() {

                @Override
                public List<ArticleModel> handle(ResultSet rs) throws SQLException {
                    List<ArticleModel> list = new ArrayList<>();
                    while (rs.next()) {
                        ArticleModel article = new ArticleModel();
                        article.setaId(rs.getInt(1));
                        article.setTitle(rs.getString(3));
                        article.setDesc(rs.getString(4));
                        article.setImg(rs.getString(5));
                        article.setUrl(rs.getString(6));
                        list.add(article);
                    }
                    return list;
                }
            }, category.getCategoryId());
        } catch (SQLException e) {
            list = new ArrayList<>();
            e.printStackTrace();
        }
        category.setArticles(list);
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
        boolean result = false;
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSorce());
        try {
            int update = queryRunner.update("INSERT INTO t_category(cName) VALUES(?)", cName);
            if (update > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteCategory(int cId) {
        boolean result = false;

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSorce());
        try {
            int update = queryRunner.update("DELETE FROM t_category WHERE cId = ?", cId);
            if (update > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean editCategory(int cId, String cName) {
        boolean result = false;


        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSorce());
        try {
            int update = queryRunner.update("UPDATE t_category set cName = ? WHERE cId = ?", cName, cId);
            if (update > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteArticle(int aId) {
        boolean result = false;

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSorce());
        try {
            int update = queryRunner.update("DELETE FROM t_article WHERE id = ?", aId);
            if (update > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public boolean addFileInfoToDatabase(FileModel file) {
        boolean result = false;

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSorce());
        try {
            int update = queryRunner.update("INSERT INTO t_file(uuid,name,file) VALUES (?, ?, ?)",
                    file.getUuid(),
                    file.getFileName(),
                    file.getFilePath());
            if (update > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean addArticle(int cId, String title, String desc, String img, String uuid) {

        boolean result = false;

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSorce());
        try {
            int update = queryRunner
                    .update("insert into t_article(categoryId,title,c_desc,img,file) values(?, ?, ?, ?, ?)",
                            cId,
                            title,
                            desc,
                            img,
                            uuid);
            if (update > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteFile(String uuid) {


        boolean result = false;

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSorce());
        try {
            int update = queryRunner.update("DELETE FROM t_file WHERE uuid = ?", uuid);
            if (update > 0)
                result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
