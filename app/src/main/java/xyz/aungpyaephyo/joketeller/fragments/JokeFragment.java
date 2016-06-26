package xyz.aungpyaephyo.joketeller.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.aungpyaephyo.joketeller.R;
import xyz.aungpyaephyo.joketeller.data.models.JokeModel;
import xyz.aungpyaephyo.joketeller.data.vos.JokeVO;

/**
 * A placeholder fragment containing a simple view.
 */
public class JokeFragment extends Fragment {

    private static final String BARG_JOKE_INDEX = "BARG_JOKE_INDEX";

    private int jokeIndex;
    private JokeVO joke;

    public static JokeFragment newInstance(int jokeIndex) {
        JokeFragment fragment = new JokeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BARG_JOKE_INDEX, jokeIndex);
        fragment.setArguments(bundle);
        return fragment;
    }

    public JokeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            jokeIndex = bundle.getInt(BARG_JOKE_INDEX);
            joke = JokeModel.getInstance().getJoke(jokeIndex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView tvJokeTitle = (TextView) view.findViewById(R.id.tv_joke_title);
        tvJokeTitle.setText(joke.getJokeTitle());

        TextView tvJokeContent = (TextView) view.findViewById(R.id.tv_joke_content);
        tvJokeContent.setText(joke.getJokeContent());

        ImageView ivJoke = (ImageView) view.findViewById(R.id.iv_joke);
        ivJoke.setImageResource(joke.getJokeImage());

        return view;
    }
}
