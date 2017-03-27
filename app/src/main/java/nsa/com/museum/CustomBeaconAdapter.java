//package nsa.com.museum;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class CustomBeaconAdapter extends BaseAdapter implements Filterable{
//
//    List<String> dataSource, originalSource;
//    LayoutInflater inflater;
//    DBConnector idh;
//
//    public CustomBeaconAdapter(Context c) {
//        idh = new DBConnector(c);
//        Map<Long, String> beacons = idh.getBeacons(null);
//        this.dataSource = new ArrayList<>();
//        this.dataSource.addAll(beacons.values());
//        inflater = LayoutInflater.from(c);
//    }
//
//    public void addBeacon (String city, String open, String close) {
//        Long i = this.idh.addMuseum(city, open, close);
//        this.notifyDataSetChanged();
//    }
//
//    @Override
//    public int getCount() {
//        return dataSource.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return dataSource.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public View getView(int position, View view, ViewGroup viewGroup) {
//
//        view = inflater.inflate(R.layout.list_row, null);
//
//        TextView txtTitle = (TextView) view.findViewById(R.id.name);
//        ImageView image = (ImageView) view.findViewById(R.id.icon);
//
//        txtTitle.setText(dataSource.get(position));
////        image.setImageResource(dataSource.get(position));
//        return view;
//
//    }
//
//    @Override
//    public Filter getFilter() {
////
////        Filter f = new Filter();
////            @Override
////            protected FilterResults performFiltering(CharSequence filterTerm) {
////            FilterResults fr = new FilterResults();
//
////        }
////
//        return null;
//    }
//
////    @Override
////    protected  void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
////        dataSource = (List<String>) filterResults.values();
////        notifyDataSetChanged();
////    }
//
////    extratxt.setText("Description "+itemname[position]);
//}