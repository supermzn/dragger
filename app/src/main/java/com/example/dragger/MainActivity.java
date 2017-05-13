package com.example.dragger;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.centerButton)
    Button centerButton;
    @BindView(R.id.target1)
    Button target1;
    @BindView(R.id.target2)
    Button target2;
    @BindView(R.id.target3)
    Button target3;
    @BindView(R.id.target4)
    Button target4;

    private final String TAG = getClass().getSimpleName();

    private float mXdifference;
    private float mYdifference;
    private List<Button> mTargetList;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTargetList = initTargetList();

        centerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        mXdifference = v.getX() - event.getRawX();
                        mYdifference = v.getY() - event.getRawY();
                        v.setPressed(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        v.setX(event.getRawX() + mXdifference);
                        v.setY(event.getRawY() + mYdifference);
                        checkTargetTouching();
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        View parent = (View) v.getParent();
                        v.setX((parent.getWidth() / 2 - getRadius(v)));
                        v.setY((parent.getHeight() / 2 - getRadius(v)));
                }
                return true;
            }
        });
    }

    private void checkTargetTouching() {
        for (int i = 0; i < mTargetList.size(); i++) {
            Button tmpTarget = mTargetList.get(i);
            double tmpDistance = getDistance(getPosition(centerButton), getPosition(tmpTarget));
            if (isTouchingTarget(tmpTarget, tmpDistance)) {
                onTargetReached(i);
                return;
            }
        }
    }

    private List<Button> initTargetList() {
        List<Button> targetList = new ArrayList<>();
        targetList.add(target1);
        targetList.add(target2);
        targetList.add(target3);
        targetList.add(target4);
        String buttonText = getString(R.string.target);
        for (int i = 0; i < targetList.size(); i++) {
            targetList.get(i).setText(buttonText + (i + 1));
        }
        return targetList;
    }

    private boolean isTouchingTarget(View target, double distance) {
        return distance <= getRadius(centerButton) + getRadius(target);
    }

    private Pair<Float, Float> getPosition(View view) {
        return new Pair<>(view.getX() + getRadius(view),
                view.getY() + getRadius(view));
    }

    private float getRadius(View view) {
        if (view.getHeight() == view.getWidth())
            return view.getHeight() / 2;
        else throw new IllegalStateException(getString(R.string.not_square_exception));
    }

    private void onTargetReached(int index) {
        Log.i(TAG, "Target " + (index + 1) + " reached");
     }

    private double getDistance(Pair<Float, Float> point1, Pair<Float, Float> point2) {
        double xDistance = Math.pow((point1.first - point2.first), 2);
        double yDistance = Math.pow((point1.second - point2.second), 2);
        return Math.sqrt(xDistance + yDistance);
    }
}
