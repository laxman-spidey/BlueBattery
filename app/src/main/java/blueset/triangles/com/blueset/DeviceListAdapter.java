package blueset.triangles.com.blueset;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import blueset.triangles.com.blueset.util.LogUtil;

/**
 * Created by mittu on 3/15/2015.
 */
public class DeviceListAdapter extends ArrayAdapter<BluetoothDevice> {


    private  Context context;
    private  String[] values;
    //private final ArrayList<Integer> imageIdList;

    HashMap<String, Integer> mIdMap;


    public DeviceListAdapter(Context context, int resource) {
        super(context, resource);
        this.context = context;
       // mIdMap = new HashMap<String, Integer>();
        //int i=0;
        /*int size = deviceList.getCount();

        LogUtil.print(" " + size);
        values = new String[size];

        for(i = 0; i<size;i++)
        {
            BluetoothDevice device = deviceList.getItem(i);
            values[i] = device.getName();
            mIdMap.put(device.getName(),i);
        }
        */
    }

    @Override
    public long getItemId(int position)
    {
        //BluetoothDevice item = getItem(position);
        //return mIdMap.get(item);
        return super.getItemId(position);

    }

    @Override
    public int getCount()
    {
        // TODO Auto-generated method stub
        //Log.i("tag", "item Length=" + values.length);
        //return values.length;
        return super.getCount();
    }
    static int i=0;
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //Log.i("tag","getview");
        BluetoothDevice device = this.getItem(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_layout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.rowItemLabel);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.rowItemIcon);
        textView.setText(device.getName() + "\n" + device.getAddress());
        //Log.i("tag", "item "+(position)+"   "+imageIdList.get(position));
        //imageView.setBackgroundResource(imageIdList.get(position));
        //Log.i("tag","endgetview");
        return rowView;
    }

    @Override
    public BluetoothDevice getItem(int position)
    {
        // TODO Auto-generated method stub
        return super.getItem(position);
    }

}
