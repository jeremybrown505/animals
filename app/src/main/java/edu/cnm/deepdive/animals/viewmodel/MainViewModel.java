package edu.cnm.deepdive.animals.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.animals.BuildConfig;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.service.AnimalService;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {

  private MutableLiveData<List<Animal>> animals;

  public MainViewModel(@NonNull Application application) {
    super(application);
    animals = new MutableLiveData<>();
    loadAnimals();
  }

  public LiveData<List<Animal>> getAnimals() {
    return animals;
  }

  private void loadAnimals() {
    new AsyncTask<Void, Void, List<Animal>>() {
      AnimalService animalService;

      @Override
      protected void onPreExecute() {
        super.onPreExecute();
        Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
        animalService = retrofit.create(AnimalService.class);
      }

      @Override
      protected List<Animal> doInBackground(Void... voids) {
        try {
          Response<List<Animal>> response = animalService.getAnimals(BuildConfig.CLIENT_KEY)
              .execute();
          if (response.isSuccessful()) {
            List<Animal> animals = response.body();
            assert animals != null;
            MainViewModel.this.animals.postValue(animals);
            return animals;
          } else {
            Log.e("AnimalService", response.message());
            cancel(true);
          }
        } catch (IOException e) {
          Log.e("AnimalService", e.getMessage(), e);
          cancel(true);
        }
        return null;
      }
    }.execute();
  }
}
