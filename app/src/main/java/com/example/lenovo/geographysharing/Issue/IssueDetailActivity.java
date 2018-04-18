package com.example.lenovo.geographysharing.Issue;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.geographysharing.BaseClass.BaseActivity;
import com.example.lenovo.geographysharing.EntityClass.Issue;
import com.example.lenovo.geographysharing.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by lenovo on 2018/1/2.
 */

public class IssueDetailActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_Upload_Pic; //上传图片按钮
    private Button btn_Publish; //发布设备按钮
    private ImageView img = null;//加载设备的图片


    // 返回码：系统图库
    private static final int RESULT_IMAGE = 100;
    // 返回码：相机
    private static final int RESULT_CAMERA = 200;

    private Uri imageUri = Uri.parse("content://media/external/images/media/1");

    private File currentImageFile = null;//图片压缩后储存的位置

    private File outputImage = null;//相机拍摄图片的缓存位置

    private String filename = null;//压缩后的文件名，用于上传直接调用。



    @Override
    protected int getLayoutId() {
        return R.layout.activity_issue_details;
    }

    @Override
    protected void initView() {
        setSupportActionBar();//表示当前页面支持ActionBar
        setSupportArrowActionBar(true);
        setTitle("填写发布设备信息");
        img = bindViewId(R.id.issue_detail_pic);
        btn_Publish = bindViewId(R.id.issue);//发布设备按钮
        btn_Upload_Pic = bindViewId(R.id.issue_pic);//上传图片按钮

        btn_Publish.setOnClickListener(this);
        btn_Upload_Pic.setOnClickListener(this);

    /*
    * 以下被注释的方法适合一个button时用
    * */
//        btn_Upload_Pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Chose_Pic_or_Cam();
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.issue:
                //发布设备按钮实现接口
                break;
            case R.id.issue_pic:
                Chose_Pic_or_Cam();//上传图片时，拍照还是图库。
                break;
            default:
                break;

        }

    }

    private void Chose_Pic_or_Cam() {
        //将文件读写权限写在此处。
        if (ContextCompat.checkSelfPermission(IssueDetailActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IssueDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);//参数为0,表示不跳向图库activity。
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                  /*.setTitle("选择图片：")
                  * */
                .setItems(new String[]{"相机", "图库"}, new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                takePhoto();
                                break;
                            case 1:
                                openAlbum();
                                break;
                            default:
                                break;
                        }

                    }
                });
        builder.create().show();
    }

    /**
     * 打开相册
     */
    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, RESULT_IMAGE);
    }

    /**
     * 拍摄
     */
    private void takePhoto() {
        outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
         if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(IssueDetailActivity.this, "com.example.lenovo.geographysharing.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //测试点
        Toast.makeText(IssueDetailActivity.this, imageUri.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, RESULT_CAMERA);
    }

    /**
     * 图库访问许可后调用图片    此APP未用
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            }
        }
    }


    /**
     * 回调
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            try {
                CreateFiles();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (requestCode == RESULT_IMAGE && data != null) {
                // 相册
                if (Build.VERSION.SDK_INT >= 19) {//版本兼容
                    handleImageOnKitKat(data);
                } else {
                    handleImageBeforeKitKat(data);
                }
            } else if (requestCode == RESULT_CAMERA) {
                // 相机
                    //Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    Bitmap showBitmap = getSmallBitmap_File(outputImage,245,180);//压缩图片
                    img.setImageBitmap(showBitmap);
            }
        }


    }

    /**
     * 相册选择图片
     * 大于4.4版本SDK
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String documentId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                imagePath = getImagePath(contentUri, null);
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                imagePath = uri.getPath();
            }
            displayImage(imagePath);
        }
    }

    /**
     * 相册选择图片
     * 小于于4.4版本SDK
     */
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    /**
     * 获取相册图片路径
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));

                Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 显示图片
     */
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            //Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//显示缩略图
            //Bitmap showbitmap = ThumbnailUtils.extractThumbnail(bitmap, 245, 180);//显示缩略图

            Bitmap showBitmap = getSmallBitmap_String(imagePath,245,180);
            img.setImageBitmap(showBitmap);

        } else {
            Toast.makeText(this, "请选择正确的图片路径！", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void initData() {

    }

    public static void launchIssueDetailActivity(Activity activity) {
        Intent intent = new Intent(activity, IssueDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
    }

/*
压缩

 */
//采样率压缩（根据路径获取图片并压缩）：
public Bitmap getSmallBitmap_File(File file, int reqWidth, int reqHeight) {//针对相机
    try {
        String filePath = file.getAbsolutePath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//开始读入图片，此时把options.inJustDecodeBounds 设回true了
        BitmapFactory.decodeFile(filePath, options);//此时返回bm为空
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);//设置缩放比例 数值越高，图片像素越低
        options.inJustDecodeBounds = false;//重新读入图片，注意此时把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        compressImage(bitmap);
        return bitmap;
    } catch (Exception e) {
        Log.d("wzc", "类:" + this.getClass().getName() + " 方法：" + Thread.currentThread()
                .getStackTrace()[0].getMethodName() + " 异常 " + e);
        return null;
    }
}
    public Bitmap getSmallBitmap_String(String filePath, int reqWidth, int reqHeight) {//针对相册
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//开始读入图片，此时把options.inJustDecodeBounds 设回true了
            BitmapFactory.decodeFile(filePath, options);//此时返回bm为空
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);//设置缩放比例 数值越高，图片像素越低
            options.inJustDecodeBounds = false;//重新读入图片，注意此时把options.inJustDecodeBounds 设回false了
            Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            compressImage(bitmap);
            return bitmap;
        } catch (Exception e) {
            Log.d("wzc", "类:" + this.getClass().getName() + " 方法：" + Thread.currentThread()
                    .getStackTrace()[0].getMethodName() + " 异常 " + e);
            return null;
        }
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        try {
            int height = options.outHeight;
            int width = options.outWidth;
            int inSampleSize = 1;  //1表示不缩放
            if (height > reqHeight || width > reqWidth) {
                int heightRatio = Math.round((float) height / (float) reqHeight);
                int widthRatio = Math.round((float) width / (float) reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            return inSampleSize;
        } catch (Exception e) {
            Log.d("wzc", "类:" + this.getClass().getName() + " 方法：" + Thread.currentThread()
                    .getStackTrace()[0].getMethodName() + " 异常 " + e);
            return 1;
        }
    }

    // 质量压缩法：
    private Bitmap compressImage(Bitmap image) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while (baos.toByteArray().length / 1024 > 100) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
                baos.reset();//重置baos即清空baos
                options -= 10;//每次都减少10
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

            }
            //压缩好后写入文件中
            FileOutputStream fos = new FileOutputStream(currentImageFile.getAbsolutePath());
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    private void CreateFiles() throws IOException {
        File dir = new File(Environment.getExternalStorageDirectory(), "GeoShare");//在sd下创建文件夹myimage；Environment.getExternalStorageDirectory()得到SD卡路径文件
        if (!dir.exists()) {    //exists()判断文件是否存在，不存在则创建文件
            dir.mkdirs();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式在android中，创建文件时，文件名中不能包含“：”冒号
        filename = df.format(new Date());
        currentImageFile = new File(dir, filename + ".jpg");
        if (!currentImageFile.exists()) {
            currentImageFile.createNewFile();
        }
    }



}

