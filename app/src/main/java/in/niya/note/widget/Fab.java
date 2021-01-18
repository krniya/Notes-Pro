package in.niya.note.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import in.niya.note.App;
import in.niya.note.R;
import in.niya.note.inner.Animator;

public class Fab extends AppCompatImageView {
	private boolean isHidden = false;

	public Fab(Context context) {
		super(context);
	}

	public Fab(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Fab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	public void show() {
		if (isHidden) {
			isHidden = false;
			Animator.create(getContext().getApplicationContext())
					.on(this)
					.setStartVisibility(View.VISIBLE)
					.animate(R.anim.fab_scroll_in);
		}
	}

	public void hide() {
		if (App.smartFab && !isHidden) {
			isHidden = true;
			Animator.create(getContext().getApplicationContext())
					.on(this)
					.setEndVisibility(View.GONE)
					.animate(R.anim.fab_scroll_out);
		}
	}
}
