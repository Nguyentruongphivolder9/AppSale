package com.example.appsale.presentation.views.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsale.R;
import com.example.appsale.data.datasources.remote.AppResource;
import com.example.appsale.data.models.Food;
import com.example.appsale.presentation.adapters.FoodAdapter;
import com.example.appsale.presentation.views.authentications.sign_up.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel viewModel;
    LinearLayout layoutLoading;
    RecyclerView rcvFood;
    FoodAdapter foodAdapter;
    TextView tvCountCart;
    List<Food> listCart;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        layoutLoading = findViewById(R.id.layout_loading);
        rcvFood = findViewById(R.id.recyclerViewFood);
        toolbar = findViewById(R.id.toolbar_home);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white, null));

        listCart = new ArrayList<>();
        viewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(getApplicationContext());
            }
        }).get(HomeViewModel.class);
        foodAdapter = new FoodAdapter();
        rcvFood.setAdapter(foodAdapter);
        rcvFood.setHasFixedSize(true);


        viewModel.getFoods().observe(this, new Observer<AppResource<List<Food>>>() {
            @Override
            public void onChanged(AppResource<List<Food>> foodAppResource) {
                switch (foodAppResource.status){
                    case LOADING:
                        layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        layoutLoading.setVisibility(View.GONE);
                        foodAdapter.updateListProduct(foodAppResource.data);
                        break;
                    case ERROR:
                        Toast.makeText(HomeActivity.this, foodAppResource.message, Toast.LENGTH_SHORT);
                        layoutLoading.setVisibility(View.GONE);
                        break;
                }
            }
        });

        viewModel.fetchFoods();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View actionView = menuItem.getActionView();
        tvCountCart = actionView.findViewById(R.id.text_cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cart:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

    }
}
