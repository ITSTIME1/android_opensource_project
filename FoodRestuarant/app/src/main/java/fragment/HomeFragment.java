package fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodrestuarant.R;

import java.util.ArrayList;
import java.util.List;

import adapter.HomeHorAdapter;
import model.HomeHorModel;

public class HomeFragment extends Fragment {
    // HomeFragment
    // 프래그먼트 내에서 뷰를 표시할 RecyclerView, 데이터 값이 저장되어 있는 HomeHorModel, 뷰를 만들어주는 작업을 하는 매개체 HomeHorAdapter

    private RecyclerView homeHorRec;
    private List<HomeHorModel> homeHorModelList;
    private HomeHorAdapter homeHorAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeRoot = inflater.inflate(R.layout.fragment_home, container, false);

        // RecyclerView 참조
        homeHorRec = homeRoot.findViewById(R.id.home_hor_rec);
        // RecyclerView 어떻게 보여질지 LayoutManager를 통해서 결정
        homeHorRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        // RecyclerView에 표시할 데이터를 임의로 만들어보자
        homeHorModelList = new ArrayList<>();
        homeHorModelList.add(new HomeHorModel(R.drawable.hambuger,"Hambuger"));
        homeHorModelList.add(new HomeHorModel(R.drawable.pastar,"Pastar"));
        homeHorModelList.add(new HomeHorModel(R.drawable.steak,"Steak"));
        homeHorModelList.add(new HomeHorModel(R.drawable.salad,"Salad"));

        // RecyclerView에 이제 표시하기 위해서 Adapter를 만들자.
        homeHorAdapter = new HomeHorAdapter(getActivity(), homeHorModelList);
        homeHorRec.setAdapter(homeHorAdapter);
        homeHorRec.setHasFixedSize(true);
        // 중첩 스크롤 문제로 인해 스크롤이 잘 되지 않는걸 방지하기 위해 false값으로 설정한다.
        homeHorRec.setNestedScrollingEnabled(false);
        return homeRoot;
    }
}
