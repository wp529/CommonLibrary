package com.wp.commonlibrary;

import android.content.Context;

import com.wp.commonlibrary.baseMVP.BaseActivity;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by WangPing on 2018/2/7.
 */

public class ActivityManager {

    private static Stack<BaseActivity> activityStack;
    private static ActivityManager instance;

    private ActivityManager() {
        activityStack = new Stack<>();
    }

    /**
     * 单一实例
     */
    public static ActivityManager getAppManager() {
        if (instance == null) {
            synchronized (ActivityManager.class) {
                if (instance == null)
                    instance = new ActivityManager();
            }
        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(BaseActivity activity) {
        activityStack.add(activity);
    }

    /**
     * 将activity移除
     */
    public void removeActivity(BaseActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 栈顶Activity
     */
    public BaseActivity topActivity() {
        return activityStack.lastElement();
    }

    /**
     * 关闭栈顶Activity
     */
    public void finishTopActivity() {
        BaseActivity activity = activityStack.lastElement();
        finishSpecifyActivityByInstance(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishSpecifyActivityByInstance(BaseActivity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishSpecifyActivityByClass(Class<?>... cls) {
        Iterator<BaseActivity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            BaseActivity integer = iterator.next();
            for (Class<?> c : cls) {
                if (integer.getClass().equals(c)) {
                    iterator.remove();
                    integer.finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        finishAllActivity();
        System.exit(0);
    }
}
