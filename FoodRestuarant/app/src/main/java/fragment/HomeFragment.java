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
import adapter.HomeVerAdapter;
import model.HomeHorModel;
import model.HomeVerModel;

public class HomeFragment extends Fragment {
    // HomeFragment
    // 프래그먼트 내에서 뷰를 표시할 RecyclerView, 데이터 값이 저장되어 있는 HomeHorModel, 뷰를 만들어주는 작업을 하는 매개체 HomeHorAdapter

    private RecyclerView homeHorRec;
    private RecyclerView homeVerRec;
    private List<HomeHorModel> homeHorModelList;
    private ArrayList<HomeVerModel> homeVerModelList;
    private HomeHorAdapter homeHorAdapter;
    private HomeVerAdapter homeVerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View homeRoot = inflater.inflate(R.layout.fragment_home, container, false);

        // RecyclerView 참조 (Hor, Ver)
        homeHorRec = homeRoot.findViewById(R.id.home_hor_rec);
        homeVerRec = homeRoot.findViewById(R.id.home_ver_rec);
        // RecyclerView 어떻게 보여질지 LayoutManager를 통해서 결정
        homeHorRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        homeVerRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        // RecyclerView에 표시할 데이터를 임의로 만들어보자
        homeHorModelList = new ArrayList<>();
        homeHorModelList.add(new HomeHorModel(R.drawable.hambuger,"Hambuger"));
        homeHorModelList.add(new HomeHorModel(R.drawable.pastar,"Pastar"));
        homeHorModelList.add(new HomeHorModel(R.drawable.steak,"Steak"));
        homeHorModelList.add(new HomeHorModel(R.drawable.salad,"Salad"));

        homeVerModelList = new ArrayList<>();
        homeVerModelList.add(new HomeVerModel(R.drawable.verhambuger, "New York Buger", "I'm sure you feel happy"));
        homeVerModelList.add(new HomeVerModel(R.drawable.verhambuger, "No Brand Buger", "I'm sure you feel happy"));
        homeVerModelList.add(new HomeVerModel(R.drawable.verhambuger, "Emart Buger", "I'm sure you feel happy"));
        homeVerModelList.add(new HomeVerModel(R.drawable.verhambuger, "HomePlus Buger", "I'm sure you feel happy"));

        // 데이터를 Adapter 내에서 가공하기 위해 Adapter를 만들자.
        // 여기서 getActivity()는 HomeFragment의 context 값이다.
        // this를 사용하지 않은 이유는 Fragment는 Activity가 아니기 때문이다.
        homeHorAdapter = new HomeHorAdapter(getActivity(), homeHorModelList);
        homeVerAdapter = new HomeVerAdapter(getActivity(), homeVerModelList);
        // 데이터를 넣어준 변수를 가지고 setAdapter()메서드에 넣어준다.
        homeHorRec.setAdapter(homeHorAdapter);
        homeHorRec.setHasFixedSize(true);
        homeVerRec.setAdapter(homeVerAdapter);
        homeVerRec.setHasFixedSize(true);
        // 중첩 스크롤 문제로 인해 스크롤이 잘 되지 않는걸 방지하기 위해 false값으로 설정한다.
        homeHorRec.setNestedScrollingEnabled(false);
        homeVerRec.setNestedScrollingEnabled(false);


        return homeRoot;
    }
}
