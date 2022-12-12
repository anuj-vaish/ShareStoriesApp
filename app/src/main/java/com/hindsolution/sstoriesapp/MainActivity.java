package com.hindsolution.sstoriesapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
//        , PushSDKApplication
{
    private static final int PICK_IMAGE = 200;
    String shareLink = "https://www.youtube.com/c/CheezyCode";
    String shareStoriesPhotoURl = "";
    ShareButton sb_link;
    ShareButton sb_post;
    ShareButton sb_story;
    ShareButton sb_video_story;

    ShareButton sb_insta_link;
    Button sb_insta_feed;
    Button sb_insta_story;
    ShareButton sb_insta_video_story;

    ShareButton sb_tweeter_link;
    Button sb_tweeter_feed;
    Button sb_tweeter_story;
    ShareButton sb_tweeter_video_story;

    ImageView iv_content;
    LoginButton loginButton;
    CallbackManager callbackManager;
    FloatingActionButton floatingActionButton;
    Bitmap bitmap;
    AccessToken accessToken;
    String url = "https://i.pinimg.com/564x/b2/7e/ce/b27ece35a44c9fb8b5a42e0dc2b5a7a2.jpg";
    String videoUrl = "https://www.youtube.com/c/CheezyCode";
    Uri videoFileUri;
    AccessTokenTracker accessTokenTracker;
    String MEDIA_TYPE_JPEG = "image/jpeg";
    String appId = "702647424223529";
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.setClientToken(String.valueOf(R.string.facebook_client_token));
        FacebookSdk.sdkInitialize(getApplicationContext());
        accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        iv_content = findViewById(R.id.iv_content);
        loginButton = findViewById(R.id.lb_login);
        sb_post = findViewById(R.id.sb_post);
        sb_story = findViewById(R.id.sb_story);
        sb_video_story = findViewById(R.id.sb_video_story);
        sb_link = findViewById(R.id.sb_link);
        //
        sb_insta_feed = findViewById(R.id.sb_insta_feed);
        sb_insta_story = findViewById(R.id.sb_insta_story);

        floatingActionButton = findViewById(R.id.pick_image_fb);
        iv_content.setImageResource(R.drawable.invitation);

        videoFileUri = Uri.parse(videoUrl);

        BitmapDrawable bitmapDrawable = (BitmapDrawable) iv_content.getDrawable();
        bitmap = bitmapDrawable.getBitmap();

/*
        //share content link
        linkFBShare();

        //share content post
        photoFBShare();

        //share content story
        storyFBShare();

        //share video content
        videoFBShare();
        //---------------Instagram-----------------
        sb_insta_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                shareInstaFeed();
//                shareInstaFeed(url);
                pickImage();
            }
        });
        sb_insta_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                shareInstaBackgroundAssetsAndStickers();
            }
        });


//        accessTokenTracker = new AccessTokenTracker(){

//            @Override
//            protected void onCurrentAccessTokenChanged(@Nullable AccessToken accessToken, @Nullable AccessToken currentToken) {
//                if(currentToken==null){
//                    LoginManager.getInstance().logOut();
//                }
//            }
//        };
*/
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                       /* String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        */
                    }
                });
    }

 /*  private void shareInstaFeed(Uri selectedImageUri) {
        Intent intent = new Intent("com.instagram.share.StoryContent");
        intent.putExtra("source_application", appId);
        intent.setDataAndType(selectedImageUri, MEDIA_TYPE_JPEG);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Activity activity = this;

//        if (activity.getPackageManager().resolveActivity(intent, 0) == null) {
        activity.startActivityForResult(intent, 0);
//        }

/*        Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.share.StoryContent");
        if (intent != null)
        {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage("com.instagram.android");
            try {
                shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), url, "feed", "feedDiscriptions")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            shareIntent.setType("image/jpeg");
            startActivity(shareIntent);
        }
        else
        {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id="+"com.instagram.android"));
            startActivity(intent);
        }
}
 */


       private void shareInstaFeed(Uri selectedImageUri) {
           String type = "image/*";
//           String filename = "/myPhoto.jpg";
//           String mediaPath = Environment.getExternalStorageDirectory() + filename;
//           createInstagramIntent(type, mediaPath);
           Intent share = new Intent(Intent.ACTION_SEND);
           share.setType(type);
           share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
           share.putExtra(Intent.EXTRA_STREAM, selectedImageUri);
           startActivity(Intent.createChooser(share, "Share to"));

       }

/*
    private void createInstagramIntent(String type, String mediaPath) {

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File media = new File(mediaPath);
        Uri uri = Uri.fromFile(media);

        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));

    }

    private void shareInstaBackgroundAssetsAndStickers(Uri uri) {
        //  Instantiate an intent

        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.putExtra("source_application", appId);
        intent.setDataAndType(uri, MEDIA_TYPE_JPEG);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Activity activity = this;

//        if (activity.getPackageManager().resolveActivity(intent, 0) == null) {
        activity.startActivityForResult(intent, 0);
//        }
//        else{x
    }


    private void videoFBShare() {
        ShareVideo shareVideo = new ShareVideo.Builder()
                .setLocalUrl(Uri.parse(videoUrl))
                .build();
        ShareVideoContent content = new ShareVideoContent.Builder()
                .setVideo(shareVideo)
                .build();
        sb_video_story.setShareContent(content);
    }

    private void linkFBShare() {
        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(shareLink))
                .build();

        sb_link.setShareContent(shareLinkContent);
    }

    private void storyFBShare() {
        Uri imageUri = Uri.parse(url);
        SharePhoto photo = new SharePhoto.Builder().setImageUrl(imageUri).build();

        // Add to ShareStoryContent
        ShareStoryContent shareStoryContent = new ShareStoryContent.Builder()
                .setBackgroundAsset(photo)
                .build();

        sb_story.setShareContent(shareStoryContent);
    }
*/
    private void pickImage() {
//        Intent intent = new Intent();
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/* video/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

/*
    private void photoFBShare() {
        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();

        SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();
        sb_post.setShareContent(sharePhotoContent);

    }

*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            // Get the url of the image from data
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // update the preview image in the layout
//                iv_content.setImageURI(selectedImageUri);
//                shareData(selectedImageUri);
//                if(selectedImageUri!=null){
//                    shareInstaBackgroundAssetsAndStickers(selectedImageUri);
//                }
                shareInstaFeed(selectedImageUri);

            }
        }
    }




/*    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "scv20221013_142723", null);
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Title");
        Uri path = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return path;
    }

*/

/*    private void shareData() {
// Define photo or video asset URI
        Uri backgroundAssetUri = getImageUri(this, bitmap);

// Instantiate implicit intent with ADD_TO_STORY action
        Intent intent = new Intent("com.facebook.stories.ADD_TO_STORY");
        intent.setDataAndType(backgroundAssetUri, "image/jpeg");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", appId);

// Instantiate activity and verify it will resolve implicit intent
        Activity activity = this;
        if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, 0);
        }
    }
*/
 /*   private void shareData(Uri uri) {
// Define photo or video asset URI
//        Uri backgroundAssetUri = getImageUri(this, bitmap);

// Instantiate implicit intent with ADD_TO_STORY action
        Intent intent = new Intent("com.facebook.stories.ADD_TO_STORY");
//        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
        intent.setDataAndType(uri, "image/jpeg");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("com.facebook.platform.extra.APPLICATION_ID", appId);

// Instantiate activity and verify it will resolve implicit intent
        Activity activity = this;
        if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, 0);
        }
    }
*/

    public void pickImage(View view) {
        pickImage();
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
 /*       if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
  */
    }

}