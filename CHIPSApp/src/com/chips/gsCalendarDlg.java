package com.chips;

import com.chips.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.view.View.OnClickListener;

	public class gsCalendarDlg extends Dialog implements OnClickListener
    {		
		/*public class myDialog extends gsCalendar
		{
			public myDialog(Context context, LinearLayout layout) 
			{
				super(context, layout);
			}

			@Override
			public void myClickEvent(int yyyy, int MM, int dd) 
			{
				//Log.d( "", yyyy + "" + MM + "" + dd + "") ;
				
				//gsCalendarDlg.this.cancel( ) ;
				
				super.myClickEvent(yyyy, MM, dd);
			}
		}*/
    	
		private TextView text;
		
		public gsCalendarDlg( Context context ) 
		{
			super(context);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.calendar_dlg);
			
	        //LinearLayout lv = (LinearLayout)findViewById( R.id.calendar_dia ) ;
	        
	        //text = (TextView)findViewById( R.id.dia_textview1 );
	        
	       /* tvs = new TextView[1];
	        tvs[0] = (TextView)findViewById( R.id.dia_textview1 ) ;
	        
	        myDialog dia = new myDialog(context, lv);
	        dia.setTvs(tvs);*/
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			/*if(v == text){
				gsCalendarDlg.this.cancel();
				super.dismiss();
				super.cancel();
				this.cancel();
				this.dismiss();
			}*/

			//gsCalendarDlg.this.cancel();
			//super.dismiss();
			//super.cancel();
		}
		
    }