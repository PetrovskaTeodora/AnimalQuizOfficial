package teodora.petrovska.animalquiz3;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainActivityFragment extends Fragment {

    private static final int NUMBER_OF_ANIMALS_INCLUDED_IN_QUIZ=10;

    private List<String> allAnimalsNamesList;
    private List<String> animalsNamesQuizList;
    private Set<String> animalTypesInQuiz;
    private String correctAnimalsAnswer;
    private int numberOfAllGuesses;
    private int numberOfRightAnswers;
    private int numberOfAnimalGuessRows;
    private SecureRandom secureRandomNumber;
    private Handler handler;
    private Animation wrongAnswerAnimation;

    private LinearLayout animalQuizLinearLayout;
    private TextView txtQuestionNumber;
    private ImageView imgAnimal;
    private LinearLayout[] rowsOfGuessButtonsInAnimalQuiz;
    private TextView txtAnswer;



    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main_activity, container, false);

        allAnimalsNamesList=new ArrayList<>();
        animalsNamesQuizList=new ArrayList<>();
        secureRandomNumber=new SecureRandom();
        handler=new Handler();

        wrongAnswerAnimation= AnimationUtils.loadAnimation(getActivity(), R.anim.wrong_answer_animation);
        wrongAnswerAnimation.setRepeatCount(1);

        animalQuizLinearLayout=(LinearLayout) view.findViewById(R.id.animalQuizLinearLayout);
        txtQuestionNumber=(TextView) view.findViewById(R.id.txtQuestionNumber);
        imgAnimal=(ImageView) view.findViewById(R.id.imgAnimal);
        rowsOfGuessButtonsInAnimalQuiz=new LinearLayout[3];
        rowsOfGuessButtonsInAnimalQuiz[0]= (LinearLayout) view.findViewById(R.id.firstRowLinearLayout);
        rowsOfGuessButtonsInAnimalQuiz[1]=(LinearLayout) view.findViewById(R.id.secondRowLinearLayout);
        rowsOfGuessButtonsInAnimalQuiz[2]=(LinearLayout) view.findViewById(R.id.thirdRowLinearLayout);
        txtAnswer=(TextView) view.findViewById(R.id.txtAnswer);


        for(LinearLayout row : rowsOfGuessButtonsInAnimalQuiz){
            for(int column=0; column< row.getChildCount(); column++){
                Button btnGuess=(Button) row.getChildAt(column);
                btnGuess.setOnClickListener(btnGuessListener);
                btnGuess.setTextSize(24);
            }
        }

        txtQuestionNumber.setText(getString(R.string.question_text,1,NUMBER_OF_ANIMALS_INCLUDED_IN_QUIZ));

        return view;
    }


    private View.OnClickListener btnGuessListener=new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Button btnGuess=((Button) view);
            String guessValue=btnGuess.getText().toString();
            String answerValue=getTheExactAnimalName(correctAnimalsAnswer);
            ++numberOfAllGuesses;
            if(guessValue.equals(answerValue)){
                ++numberOfRightAnswers;

                txtAnswer.setText(answerValue+ "!"+" RIGHT");
                disableQuizGuessButtons();

                if(numberOfRightAnswers==NUMBER_OF_ANIMALS_INCLUDED_IN_QUIZ){

                    DialogFragment animalQuizResults=new DialogFragment(){

                        @NonNull
                        @Override
                        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                            builder.setMessage(getString(R.string.results_string_value, numberOfAllGuesses, (1000/(double) numberOfAllGuesses)));

                            builder.setPositiveButton(R.string.reset_animal_quiz, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    resetAnimalQuiz();
                                }
                            });

                            return builder.create();
                        }
                    };

                    animalQuizResults.setCancelable(false);
                    animalQuizResults.show(getFragmentManager(), "AnimalQuizResults");
                }
                else
                {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            animateAnimalQuiz(true);
                        }
                    },1000);

                }

            }
            else{
                imgAnimal.startAnimation(wrongAnswerAnimation);
                txtAnswer.setText(R.string.wrong_answer_message);
                btnGuess.setEnabled(false);
            }
        }
    };



    private String getTheExactAnimalName(String animalName){
        return animalName.substring(animalName.indexOf('-')+1).replace('_',' ');
    }

    private void disableQuizGuessButtons(){


        for(int row=0; row< numberOfAnimalGuessRows; row++){

            LinearLayout guessRowLinearLayout= rowsOfGuessButtonsInAnimalQuiz[row];

            for(int buttonIndex=0; buttonIndex<guessRowLinearLayout.getChildCount(); buttonIndex++){
                guessRowLinearLayout.getChildAt(buttonIndex).setEnabled(false);

            }
        }
    }




}
