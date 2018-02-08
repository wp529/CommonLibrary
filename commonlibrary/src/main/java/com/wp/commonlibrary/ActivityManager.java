package com.wp.commonlibrary;

import android.app.Activity;
import android.content.Context;
import java.util.Iterator;
import java.util.Stack;

/**
 * Created by WangPing on 2018/2/7.
 */

public class ActivityManager {

    private static Stack<Activity> activityStack;
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
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 将activity移除
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 栈顶Activity
     */
    public Activity topActivity() {
        return activityStack.lastElement();
    }

    /**
     * 关闭栈顶Activity
     */
    public void finishTopActivity() {
        Activity activity = activityStack.lastElement();
        finishSpecifyActivityByInstance(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishSpecifyActivityByInstance(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishSpecifyActivityByClass(Class<?>... cls) {
        Iterator<Activity> iterator = activityStack.iterator();
        while (iterator.hasNext()) {
            Activity integer = iterator.next();
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
