package wikokit.kiwidict.wordlist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wikokit.base.wikipedia.sql.Connect;
import wikokit.base.wikt.sql.TPage;
import wikokit.base.wikt.sql.index.IndexForeign;
import wikokit.kiwidict.KWConstants;
import wikokit.kiwidict.enwikt.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WordListArrayAdapter extends ArrayAdapter<String> {
        private final Activity context;
        
        // case I
        /** Words extracted by several letters (prefix). The values of array are important*/
        TPage[] page_array;
        
        // or case II
        /** Foreign words extracted by several letters (prefix). */
        IndexForeign[] index_foreign;
        
        // in both cases
        /* Text in the WordList rows: page_titles or "foreign_page_titles -> translations_to_native" */
        private List<String> str_rows;
        
        
        static class ViewHolder {
            public TextView text;
            //public ImageView image;
            
            public TPage tpage;
            public IndexForeign index_foreign;
        }

        public WordListArrayAdapter(Activity context, 
                                    List<String> page_titles, //String[] page_titles, 
                                    TPage[] page_array) {
            super(context, R.layout.word_list_row, page_titles);
            this.context = context;
            
            this.page_array = page_array;
            this.index_foreign = null;
            this.str_rows = page_titles; 
        }
        
        public void updateData(String[] page_titles, TPage[] page_array) {
            this.page_array = page_array;   // the values are important
            this.index_foreign = null;
                    
            this.str_rows.clear(); // ???? the values are not important - only the size of the list  
            this.str_rows.addAll(Arrays.asList(page_titles));
            
//            context.setProgressBarIndeterminateVisibility(true);
            this.notifyDataSetChanged();
        }
        
        public void updateData(String[] foreign_page_titles, IndexForeign[] index_foreign) {
            this.page_array    = null;
            this.index_foreign = index_foreign;   // the values are important
            
            this.str_rows.clear(); // the values are important too
            this.str_rows.addAll(Arrays.asList(foreign_page_titles));
            
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.word_list_row, null);
                
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.text = (TextView) rowView.findViewById(R.id.textView_word);
                //viewHolder.image = (ImageView) rowView
                //        .findViewById(R.id.ImageView01);
                
                rowView.setTag(viewHolder);
            }

            ViewHolder holder = (ViewHolder) rowView.getTag();
            
            //String s = "I love Wiktionary :)";
            String s =  KWConstants.getKiwidictName() + " " +
                        KWConstants.kiwidict_version + "\n" +
                        "Wiktionary parsed database " + Connect.getWiktionaryDumpVersion();
            
            if(null != page_array && position < page_array.length) {
                TPage tpage = page_array[position];     // String s = names[position];
                s = tpage.getPageTitle();
            } else {
                if(null != index_foreign && position < index_foreign.length) {
                    IndexForeign i_foreign = index_foreign[position];     // String s = names[position];
                    s = i_foreign.getConcatForeignAndNativeWords(" -> ");
                }
            }
            holder.text.setText(s);
            
            return rowView;
        }
    }
