package edu.cnm.deepdive.animals.service;

import edu.cnm.deepdive.animals.model.Animal;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AnimalService {

  @POST("getAnimals")
  @FormUrlEncoded
  Call<List<Animal>> getAnimals(@Field("key") String key);


}
