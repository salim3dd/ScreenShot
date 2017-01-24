package mo3ddam.com.screenshot;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RelativeLayout Relative_ScreenShot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Relative_ScreenShot = (RelativeLayout) findViewById(R.id.Relative_ScreenShot);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permission_Check();
            }
        });

    }


    public void permission_Check() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                return;
            }
        }
        SaveFile();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            SaveFile();
        } else {
            permission_Check();
        }
    }

    private void SaveFile() {
        Relative_ScreenShot.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = TackScreenShot(Relative_ScreenShot);
                try {
                    if (bitmap != null) {
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "TitleName", "");
                        Toast.makeText(MainActivity.this, "تم حفظ الصورة", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    private Bitmap TackScreenShot(View view) {
        Bitmap screenShot = null;
        try {
            int w = view.getMeasuredWidth();
            int h = view.getMeasuredHeight();
            screenShot = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(screenShot);
            view.draw(c);

        } catch (Exception e) {
        }
        return screenShot;
    }
}
