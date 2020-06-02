package edu.cnm.deepdive.animals.controller;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.animals.BuildConfig;
import edu.cnm.deepdive.animals.R;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.service.AnimalService;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageFragment extends Fragment {

  private WebView contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View root = inflater.inflate(R.layout.fragment_image, container, false);
    setupWebView(root);
    return root;
  }

  private void setupWebView(View root) {
    contentView = root.findViewById(R.id.content_view);
    contentView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
      }
    });
    WebSettings settings = contentView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    new Retreiver().start();

  }

  private class Retreiver extends Thread {

    @Override
    public void run() {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .create();
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://us-central1-apis-4674e.cloudfunctions.net/")
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build();

      AnimalService animalService = retrofit.create(AnimalService.class);

      try {
        Log.d("AnimalService", "before request");

        Response<List<Animal>> response = animalService.getAnimals(BuildConfig.CLIENT_KEY)
            .execute();
        Log.d("AnimalService","after request");

        if (response.isSuccessful()) {
          List<Animal> animals = response.body();
          Log.d("AnimalService", String.valueOf(animals));
          assert animals != null;
          final String url = animals.get(0).getUrl();
          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              contentView.loadUrl(url);
            }
          });

        } else {
          Log.e("AnimalService", response.message());

        }


      } catch (IOException e) {
        Log.e("AnimalService", e.getMessage(), e);
      }
    }

  }
}
