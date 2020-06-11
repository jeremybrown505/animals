package edu.cnm.deepdive.animals.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.animals.R;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.viewmodel.MainViewModel;
import java.util.List;

public class ImageFragment extends Fragment implements OnItemSelectedListener {

  private ImageView imageView;
  private MainViewModel viewModel;

  private Toolbar toolbar;

  private Spinner spinner;
  private List<Animal> animals;
  private int selectedAnimal = -1;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_image, container, false);
    imageView = root.findViewById(R.id.image_view);

    toolbar = root.findViewById(R.id.toolbar);
    toolbar.setTitle(R.string.app_name);

    spinner = root.findViewById(R.id.animals_spinner);
    spinner.setOnItemSelectedListener(this);

    return root;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //noinspection ConstantConditions
    viewModel = new ViewModelProvider(getActivity())
        .get(MainViewModel.class);
    viewModel.getAnimals().observe(getViewLifecycleOwner(), (animals) -> {
      ArrayAdapter<Animal> adapter = new ArrayAdapter<>(
          ImageFragment.this.getContext(), R.layout.custom_spinner_item, animals
      );
      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinner.setAdapter(adapter);
      this.animals = animals;
      if(selectedAnimal >= 0) {
        updateSelection();
      }
    });

    viewModel.getSelectedItem().observe(getViewLifecycleOwner(), (item) -> {

      if (item != selectedAnimal) {
        selectedAnimal = item;
        if (animals != null) {
          updateSelection();
        }
      }

    });
  }

  private void updateSelection() {
    spinner.setSelection(selectedAnimal);
    Picasso.get().load(animals.get(selectedAnimal).getUrl()).into(imageView);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
    viewModel.select(pos);
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}

