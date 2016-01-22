package com.macrew.imageloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.swissapp.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.util.Log;
import android.widget.ImageView;


public class ImageLoader {
        
        MemoryCache memoryCache=new MemoryCache();
        FileCache fileCache;
        private Map<ImageView, String> imageViews=Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
        ExecutorService executorService; 
         
        public ImageLoader(Context context){
            fileCache=new FileCache(context);
            executorService=Executors.newFixedThreadPool(5);
        }
         
       
        int stub_id1=R.drawable.ic_launcher;
        
        /**
         * Using DisplayImage(Url, ImageView) method of 
         * ImageLoader class, you can load and cache image.
         *  You just need to provide the web url of image
         *   and the imageview in which you want to display loaded image.
         * @param url
         * @param imageView
         */
        
        public void DisplayImage1(String url,int loader, ImageView imageView ,int width , int height)
        {
        	 stub_id1 = loader;
            imageViews.put(imageView, url);
            Bitmap bitmap=memoryCache.get(url);
            Log.e("bitmap===",""+bitmap);
            //bitmap =getResizedBitmap(bitmap ,width , height);
           
            if(bitmap!=null){
            	
               imageView.setImageBitmap(bitmap);
            }
            	
            else
            {
               queuePhoto(url, imageView);
             
              imageView.setImageResource(stub_id1);
            
            }
        }
        
        public void DisplayImage(String url,int loader, ImageView imageView )
        {
        	 stub_id1 = loader;
            imageViews.put(imageView, url);
            Bitmap bitmap=memoryCache.get(url);
            
       
           
            if(bitmap!=null){
            	
               imageView.setImageBitmap(bitmap);
            }
            	
            else
            {
               queuePhoto(url, imageView);
             
              imageView.setImageResource(stub_id1);
            
            }
        }
             
        private void queuePhoto(String url, ImageView imageView)
        {
            PhotoToLoad p=new PhotoToLoad(url, imageView);
            executorService.submit(new PhotosLoader(p));
        }
         
        private Bitmap getBitmap(String url) 
        {
            File f=fileCache.getFile(url);
             
            //from SD cache
            Bitmap b = decodeFile(f);
            if(b!=null)
                return b;
             
            //from web
            try {
                Bitmap bitmap=null;
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                conn.setInstanceFollowRedirects(true);
                InputStream is=conn.getInputStream();
                OutputStream os = new FileOutputStream(f);
                Utils.CopyStream(is, os);
                os.close();
                bitmap = decodeFile(f);
                
                return bitmap;
            } catch (Throwable ex){
               ex.printStackTrace();
               if(ex instanceof OutOfMemoryError)
                   memoryCache.clear();
               return null;
            }
        }
     
        //decodes image and scales it to reduce memory consumption
        private Bitmap decodeFile(File f){
            try {
                //decode image size
                BitmapFactory.Options o = new BitmapFactory.Options();
                o.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(new FileInputStream(f),null,o);
                 
                //Find the correct scale value. It should be the power of 2.
                final int REQUIRED_SIZE=1024;
          
                int width_tmp=o.outWidth, height_tmp=o.outHeight;
                
                Log.e("w_temp",""+width_tmp + "h_temmp=="+height_tmp);
               
                int scale=1;
                while(true){
                    if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                        break;
                    width_tmp/=2;
                    height_tmp/=2;
                    
                    Log.e("w_temp",""+width_tmp + "h_temmp=="+height_tmp);
                    scale*=2;
                }
                 
                //decode with inSampleSize
                BitmapFactory.Options o2 = new BitmapFactory.Options();
                o2.inSampleSize=scale;
                return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
            } catch (FileNotFoundException e) {}
            return null;
        }
         
        //Task for the queue
        private class PhotoToLoad
        {
            public String url;
            public ImageView imageView;
            public PhotoToLoad(String u, ImageView i){
                url=u; 
                imageView=i;
            }
        }
         
        class PhotosLoader implements Runnable {
            PhotoToLoad photoToLoad;
            PhotosLoader(PhotoToLoad photoToLoad){
                this.photoToLoad=photoToLoad;
            }
             
            @Override
            public void run() {
                if(imageViewReused(photoToLoad))
                    return;
                Bitmap bmp=getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url, bmp);
                
            
                if(imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad);
                Activity a=(Activity)photoToLoad.imageView.getContext();
                a.runOnUiThread(bd);
            }
        }
         
        boolean imageViewReused(PhotoToLoad photoToLoad){
            String tag=imageViews.get(photoToLoad.imageView);
            if(tag==null || !tag.equals(photoToLoad.url))
                return true;
            return false;
        }
         
        //Used to display bitmap in the UI thread
        class BitmapDisplayer implements Runnable
        {
            Bitmap bitmap;
            
            PhotoToLoad photoToLoad;
            public BitmapDisplayer(Bitmap b, PhotoToLoad p){bitmap=b;photoToLoad=p;}
            public void run()
            {
                if(imageViewReused(photoToLoad))
                    return;
                if(bitmap!=null)
                {
                    photoToLoad.imageView.setImageBitmap(bitmap);
                }
                else{
                   photoToLoad.imageView.setImageResource(stub_id1);
                }
            }
        }
     
        public void clearCache() {
            memoryCache.clear();
            fileCache.clear();
        } 
        
        public Bitmap getResizedBitmap(Bitmap bm, int newWidth , int newHeight) {
        	
        	Log.e("bm====",""+bm);
        	 
        	int width = bm.getWidth();
        	 
        	int height = bm.getHeight();
        	 
        	float scaleWidth = ((float) newWidth) / width;
        	 
        	float scaleHeight = ((float) newHeight) / height;
        	 
        	// create a matrix for the manipulation
        	 
        	Matrix matrix = new Matrix();
        	 
        	// resize the bit map
        	 
        	matrix.postScale(scaleWidth, scaleHeight);
        	 
        	// recreate the new Bitmap
        	 
        	Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        	 
        	return resizedBitmap;
        	 
        	}
    }
    