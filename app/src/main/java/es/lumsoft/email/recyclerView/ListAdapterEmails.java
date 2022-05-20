        package es.lumsoft.email.recyclerView;

        import android.content.Context;

        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.recyclerview.widget.RecyclerView;

        import com.google.firebase.auth.FirebaseAuthException;

        import java.util.List;

        import es.lumsoft.email.R;
        import es.lumsoft.email.clases.Email;


        public class ListAdapterEmails extends RecyclerView.Adapter<ListAdapterEmails.ViewHolder>{

    private List<Email> mData;
    private LayoutInflater mInflater;
    private Context context;
    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClickListener(Email item);
    }

    public ListAdapterEmails(List<Email> itemList, Context context, onItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }

    public ListAdapterEmails(List<Email> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ViewHolder onCreateViewHolder (ViewGroup parent, int ViewType) {
        View view = mInflater.inflate(R.layout.list_element_email, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {
            holder.bindData(mData.get(position));
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
    }

    public void setItems(List<Email> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView estat, from, date, titol;


        ViewHolder(View itemView) {
            super(itemView);
            estat = itemView.findViewById(R.id.txtEstat);
            from = itemView.findViewById(R.id.txtFrom);
            date = itemView.findViewById(R.id.txtDate);
            titol = itemView.findViewById(R.id.txtTitol);

        }

        void bindData (final Email item) throws FirebaseAuthException {
            estat.setText(item.getEstat());
            from.setText(item.getFrom());
            date.setText(item.getDate());
            titol.setText(item.getSubject());

            itemView.setOnClickListener(view -> listener.onItemClickListener(item));

        }

    }

}
