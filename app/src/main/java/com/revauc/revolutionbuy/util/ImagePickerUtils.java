package com.revauc.revolutionbuy.util;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;


public class ImagePickerUtils extends Fragment {

    private static final long MAX_PHOTO_SIZE_ALLOWED = 10240;

    public interface OnImagePickerListener {

        void success(String name, String path);

        void fail(String message);

        void onImageRemove();
    }

    public static final String TAG = ImagePickerUtils.class.getSimpleName();
    private static final int CAMERA_PIC_REQUEST = 2000;
    private static final int IMAGE_PICKER_REQUEST = CAMERA_PIC_REQUEST + 1;
    private static final int MEMORY_PERMISSION_REQUEST = IMAGE_PICKER_REQUEST + 1;
    private OnImagePickerListener listener;
    private String mediaPath;
    private boolean shouldShowRemove;

    public static void add(@NonNull FragmentManager manager, @NonNull OnImagePickerListener listener, boolean shouldShowRemove) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(ImagePickerUtils.newInstance(listener, shouldShowRemove), TAG);
        transaction.commit();
    }

    private static ImagePickerUtils newInstance(@NonNull OnImagePickerListener listener, boolean shouldShowRemove) {
        ImagePickerUtils fragment = new ImagePickerUtils();
        fragment.setOnImagePickerListener(listener);
        fragment.setShouldShowRemove(shouldShowRemove);
        return fragment;
    }

    public void setOnImagePickerListener(OnImagePickerListener listener) {
        this.listener = listener;
    }

    public void setShouldShowRemove(boolean shouldShowRemove) {
        this.shouldShowRemove = shouldShowRemove;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isStoragePermissionGranted()) {
            showGalleryDialog();
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission is granted");
                return true;
            } else {
                Log.d(TAG, "Permission is revoked");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, MEMORY_PERMISSION_REQUEST);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.d(TAG, "Permission is granted");
            return true;
        }
    }

    private void showGalleryDialog() {
        final CharSequence[] items;

        if(!shouldShowRemove) {
             items = new CharSequence[]{"Take Photo", "Choose from Gallery"};
        }else{
             items = new CharSequence[]{"Take Photo", "Choose from Gallery", "Remove Photo"};
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")) {
                    dialog.dismiss();
                    mediaPath = BitmapUtils.scaledImagePath();
                    File file = new File(mediaPath);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                    String AUTHORITY = "com.revauc.revolutionbuy.provider";
                    Uri photoUri = FileProvider.getUriForFile(getActivity(), AUTHORITY, file);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                } else if (items[which].equals("Choose from Gallery")) {
                    dialog.dismiss();
                    Intent galleryIntent = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, IMAGE_PICKER_REQUEST);

                }else if(items[which].equals("Remove Photo")){
                    listener.onImageRemove();
                    dialog.dismiss();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private String handleCameraResult() {
        //Scale down the image to reduce size.

        String oldMedia = mediaPath;
        File file = new File(oldMedia);
        long length = file.length();
        length = length / 1024;
        if (length > MAX_PHOTO_SIZE_ALLOWED) {
            return Constants.ERROR_UNSUPPORTED_SIZE;
        } else {
            mediaPath = BitmapUtils.scaleImage(getContext(), mediaPath, BitmapUtils.DEFAULT_PHOTO_WIDH,
                    BitmapUtils.DEFAULT_PHOTO_HEIGHT);
            file.delete();

            return mediaPath != null ? "file:///" + mediaPath : null;
        }
    }


    //Convert media path to file path
    private String handleGalleryResult(Intent intent) {
        String path = BitmapUtils.getImagePath(getContext(), intent);
        if (TextUtils.isEmpty(path)) {
            listener.fail("Please select proper image.");
            return null;
        } else {
            File file1 = new File(path);
            long length = file1.length();
            length = length / 1024;
            if (length > MAX_PHOTO_SIZE_ALLOWED) {
                return Constants.ERROR_UNSUPPORTED_SIZE;
            }
            mediaPath = BitmapUtils.scaleImage(getContext(), path, BitmapUtils.DEFAULT_PHOTO_WIDH, BitmapUtils.DEFAULT_PHOTO_HEIGHT);
            return "file:///" + mediaPath;
        }
    }

//    private String handleCameraResult() {
//        mediaPath = Environment.getExternalStorageDirectory() + File.separator + "image.jpg";
//        return CameraUtil.getInstance().compressImage(mediaPath, getActivity());
//    }
//
//    private String handleGalleryResult(Intent intent) {
//        Uri selectedImageUri = intent.getData();
//        mediaPath = CameraUtil.getInstance().getGallaryPAth(selectedImageUri, getActivity());
//
//        if (TextUtils.isEmpty(mediaPath)) {
//            listener.fail("Please select proper image.");
//            return null;
//        } else {
//            return CameraUtil.getInstance().compressImage(mediaPath, getActivity());
//        }
//    }

    //Runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0) {
            if (MEMORY_PERMISSION_REQUEST == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                showGalleryDialog();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String filePath = null;

            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    filePath = handleCameraResult();
                    break;
                case IMAGE_PICKER_REQUEST:
                    filePath = handleGalleryResult(data);
                    break;
            }

            if(listener != null) {
                if (!TextUtils.isEmpty(filePath)) {
                    if (filePath.equals(Constants.ERROR_UNSUPPORTED_SIZE)) {
                        listener.fail(Constants.ERROR_CANNOT_UPLOAD);
                    } else {
                        String successFilePath = filePath.replace("file:////","/");
                        listener.success(filePath.substring(filePath.lastIndexOf("/") + 1), successFilePath);
                    }
                } else {
                    listener.fail("Unable to get path");
                }

            }else{
                if(getActivity() != null){
                    Toast.makeText(getActivity(),"Unable to get the image, Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}