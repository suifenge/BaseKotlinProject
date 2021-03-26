package com.suifeng.kotlin.base.permissions;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.subjects.PublishSubject;


public class RxPermissionsFragment extends Fragment {

    private static final int PERMISSIONS_REQUEST_CODE = 42;

    // Contains all the current permission requests.
    // Once granted or denied, they are removed from it.
    private Map<String, PublishSubject<Permission>> mSubjects = new HashMap<>();
    private boolean mLogging;

    public RxPermissionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    /**
     * 请求权限列表
     *
     * @param permissions
     */
    @TargetApi(Build.VERSION_CODES.M)
    void requestPermissions(@NonNull List<String> permissions) {
        List<String> sourcePermissions = new ArrayList<>(permissions);
        for (int i = 0; i < permissions.size(); i++) {
            String permission = permissions.get(i);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (permission.equals(Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
                    //8.0 安装权限
                    Uri packageURI = Uri.parse("package:" + getContext().getPackageName());
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                    startActivityForResult(intent, 100001);
                    // 移除
                    sourcePermissions.remove(permission);
                }
            }
        }
        if (sourcePermissions.size() > 0) {
            String[] unrequestedPermissionsArray = sourcePermissions.toArray(new String[sourcePermissions.size()]);
            requestPermissions(unrequestedPermissionsArray, PERMISSIONS_REQUEST_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != PERMISSIONS_REQUEST_CODE) return;

        boolean[] shouldShowRequestPermissionRationale = new boolean[permissions.length];

        for (int i = 0; i < permissions.length; i++) {
            shouldShowRequestPermissionRationale[i] = shouldShowRequestPermissionRationale(permissions[i]);
        }

        onRequestPermissionsResult(permissions, grantResults, shouldShowRequestPermissionRationale);
    }

    void onRequestPermissionsResult(String permissions[], int[] grantResults, boolean[] shouldShowRequestPermissionRationale) {
        for (int i = 0, size = permissions.length; i < size; i++) {
            log("onRequestPermissionsResult  " + permissions[i]);
            // Find the corresponding subject
            PublishSubject<Permission> subject = mSubjects.get(permissions[i]);
            if (subject == null) {
                // No subject found
                Log.e(RxPermissions.TAG, "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
                return;
            }
            mSubjects.remove(permissions[i]);
            boolean granted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            subject.onNext(new Permission(permissions[i], granted, shouldShowRequestPermissionRationale[i]));
            subject.onComplete();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100001) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String permission = Manifest.permission.REQUEST_INSTALL_PACKAGES;
                PublishSubject<Permission> subject = mSubjects.get(permission);
                if (subject == null) {
                    // No subject found
                    Log.e(RxPermissions.TAG, "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
                    return;
                }
                mSubjects.remove(permission);
                boolean installs = getActivity().getPackageManager().canRequestPackageInstalls();
                subject.onNext(new Permission(permission, installs, false));
                subject.onComplete();
            }

        }
    }


    /**
     * 是否存在这个权限
     *
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    boolean isGranted(String permission) {
        // 8.0 权限 安装apk 权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && permission.equals(Manifest.permission.REQUEST_INSTALL_PACKAGES)) {
            return getActivity().getPackageManager().canRequestPackageInstalls();
        }
        return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }


    /**
     * 查看清单里 是否申明了 这个权限
     *
     * @param permission
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    boolean isRevoked(String permission) {
        return getActivity().getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

    public void setLogging(boolean logging) {
        mLogging = logging;
    }

    public PublishSubject<Permission> getSubjectByPermission(@NonNull String permission) {
        return mSubjects.get(permission);
    }

    public boolean containsByPermission(@NonNull String permission) {
        return mSubjects.containsKey(permission);
    }

    public PublishSubject<Permission> setSubjectForPermission(@NonNull String permission, @NonNull PublishSubject<Permission> subject) {
        return mSubjects.put(permission, subject);
    }

    void log(String message) {
        if (mLogging) {
            Log.d(RxPermissions.TAG, message);
        }
    }

}
