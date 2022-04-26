package adapters;


import java.util.ArrayList;
import java.util.List;

import model.HomeVerticalItemModel;

// HomeHorizontalItem에 의해서 터치시 발생되는 메서드 규격.
public interface UpdateVerticalRec {
    public void callBack(int position, ArrayList<HomeVerticalItemModel> homeVerticalItemModelList);
}
