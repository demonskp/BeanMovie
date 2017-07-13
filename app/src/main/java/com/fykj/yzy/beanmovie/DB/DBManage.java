package com.fykj.yzy.beanmovie.DB;

import android.content.Context;
import android.util.Log;

import com.fykj.yzy.beanmovie.bean.CollectionBean;
import com.fykj.yzy.beanmovie.bean.HistoryBean;
import com.fykj.yzy.beanmovie.bean.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pg3 on 2017/7/4.
 */

public class DBManage {
    private static final String TAG = "DBManage";

    private static DBManage manage;
//    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private DaoMaster master;
    private DaoSession session;
    private HistoryBeanDao historyDao;
    private UserDao userDao;
    private CollectionBeanDao collectionDao;

      private DBManage(Context context){
//          this.context=context;
          helper=new DaoMaster.DevOpenHelper(context,"history_db",null);
          master=new  DaoMaster(helper.getWritableDb());
          session=master.newSession();
          historyDao=session.getHistoryBeanDao();
          userDao=session.getUserDao();
          collectionDao=session.getCollectionBeanDao();
      }

      public static DBManage getDBManage(Context context){

          if (manage==null){
              synchronized (DBManage.class){
                  if (manage==null){
                      manage=new DBManage(context);
                  }
              }
          }
          return manage;
      }


      public void insertHistory(HistoryBean historyBean){
          if (findHistory(historyBean.getSearchString())==null){
              historyDao.insert(historyBean);
          }

      }

      public HistoryBean findHistory(String searchString){
          try {
              HistoryBean result=historyDao.queryBuilder().where(HistoryBeanDao.Properties.SearchString.eq(searchString)).list().get(0);
              return result;
          }catch (Exception e){
              return null;
          }
      }

      public ArrayList<HistoryBean> listHistory(){
          List<HistoryBean> list=historyDao.queryBuilder().list();
          return new ArrayList<HistoryBean>(list);
      }


      public void removeAllHistory(){
          historyDao.deleteAll();
      }

    public void insertUser(User user) throws Exception {
        if (findUser(user.getId()).size()==0){
            userDao.insert(user);
        }else {
            throw new Exception();
        }

    }

    public List<User> findUser(long id){
        Log.d(TAG, "findUser: "+id);
        List<User> users=userDao.queryBuilder().where(UserDao.Properties.Id.eq(id)).build().list();
        return users;
    }

    public boolean login(long id,String passworld){
        List<User> users=findUser(id);

        if (users.size()==0){
            return false;
        }
        if (users.get(0).getPassWorld().equals(passworld)){
            return true;
        }else {
            return false;
        }
    }

    public void insertCollection(CollectionBean collectionBean){
        if (findCollection(collectionBean.getId()).size()==0){
            Log.d(TAG, "insertCollection: "+"插入收藏成功");
            collectionDao.insert(collectionBean);
        }else {
            Log.d(TAG, "insertCollection: "+"插入收藏失败");
        }
    }

    public List<CollectionBean> findCollection(long id){

        List<CollectionBean> collections=collectionDao.queryBuilder().where(CollectionBeanDao.Properties.Id.eq(id)).list();
        return collections;
    }

    public boolean deleteCollection(long id){
        try{
            collectionDao.deleteByKey(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

}
