package com.publiciteweb.mobile.calculette;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Vector;

public class Calculette extends AppCompatActivity implements View.OnClickListener {

    // Représentation des boutons de l'interface
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnMinus, btnPlus, btnDivide, btnSigned, btnSquare, btnEquals, btnComma, btnPourcent, btnFraction, btnMutipli, btnBack, btnCE, btnC;
    // Représentation du TextView de l'interface
    TextView txtResult;
    // Représentation des nombres entrés par l'utilisateur
    Vector<String> numbers = new Vector();
    // Représentation des opérateur entrés par l'utilisateur
    Vector<String> operators = new Vector();
    // Représentation de l'équation complète à afficher à l'utilisateur
    String equation = "";
    boolean addNumber = false;
    // Représentation du nombre actuel
    String number = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculette);
        initContentView();
    }

    @Override
    public void onClick(View view) {
        operationsActions(view);
    }

    // Cette méthode permet de prendre en compte la multipication et la division et donc la priorité des opérations de l'équation
    // Lorsque la méthode est appelé, elle remplace, dans le vecteur de nombres, tous les nombre des multiplication par la réponse
    // de la de cette opération, supprime les nombres de cette multiplication et supprime l'opérateur de multiplication à la position
    // correspondant à l'opération
    private void calculateMultiDivide() {
        Vector<String> rebuiltOperators = (Vector<String>) operators.clone();
        Vector<String> rebuiltNumbers = (Vector<String>) numbers.clone();
        double resultTmp = 0;
        double numberTmp = 0;
        int counter = 0;
        int operatorIndex = rebuiltOperators.size();
        while (counter < operatorIndex) {
            String operator = rebuiltOperators.get(counter);
            if (operator.equals("*")) {
                resultTmp = new Double(rebuiltNumbers.get(counter)).doubleValue() * new Double(rebuiltNumbers.get(counter + 1)).doubleValue();
                rebuiltOperators.remove(counter);
                rebuiltNumbers.remove(counter + 1);
                rebuiltNumbers.remove(counter);
                rebuiltNumbers.add(counter, Double.toString(resultTmp));
                counter = 0;
            } else if (operator.equals("÷")) {
                resultTmp = new Double(rebuiltNumbers.get(counter)).doubleValue() / new Double(rebuiltNumbers.get(counter + 1)).doubleValue();
                rebuiltOperators.remove(counter);
                rebuiltNumbers.remove(counter + 1);
                rebuiltNumbers.remove(counter);
                rebuiltNumbers.add(counter, Double.toString(resultTmp));
                counter = 0;
            } else {
                counter++;
            }
            operatorIndex = rebuiltOperators.size();
        }
        operators = rebuiltOperators;
        numbers = rebuiltNumbers;
    }

    // Cette méthode permet de prendre en compte les additions et les soustractions et ajoute l'opérateur à la chaine de caratères equation
    private double calculate() {
        double resultTmp = 0;
        double numberTmp = 0;
        int counter = 0;
        resultTmp = new Double(numbers.get(counter)).doubleValue();
        for (String operator : operators) {
            numberTmp = new Double(numbers.get(counter + 1)).doubleValue();
            if (operator.equals("+")) {
                resultTmp += numberTmp;
            }
            if (operator.equals("-")) {
                resultTmp -= numberTmp;
            }
            if (operator.equals("*")) {
                equation += " * ";
            }
            if (operator.equals("÷")) {
                equation += " ÷ ";
            }
            counter++;
        }
        return resultTmp;
    }

    //Retourne la valeur de la chaine de caratère equation
    private String getEquation() {
        return equation;
    }

    // Affiche dans le format donné (equation + résultat de l'expression arithmétique) la réponse dans le TextView
    private void displayResult(Double pResult) {
        txtResult.setText(getEquation() + "\n" + pResult);
        reset();
    }

    // Affiche dans le format donné (equation + résultat de l'expression arithmétique) la réponse dans le TextView
    private void displayNumber() {
        txtResult.setText(getEquation() + "\n" + formatToString(number));
    }

    // Retourne le numéro actuel qui a été tapé au clavier
    private String getNumber() {
        return number;
    }

    // Ajoute un nombre au numéro actuel
    private void addNumber(String pNumber) {
        addNumber = true;
        number += pNumber;
        displayNumber();
    }

    // Ajoute un opérateur à l'opération arithmétique
    // Si l'utlisateur entre 2 opérateurs un à la suite de l'autre le plus récent sera utlisé pour le calcul final.
    // Ajoute le nombre actuel à l'opération arithmétique
    // Affiche le résutat
    private void addOperator(String pOperator) {
        if (addNumber == true)
            numbers.add(number);

        if (operators.size() < numbers.size()) {
            equation += getNumber() + " " + pOperator + " ";
            operators.add(pOperator);
        } else {
            if (numbers.size() > 0) {
                equation = equation.substring(0, equation.length() - 3) + " " + pOperator + " ";
                operators.setElementAt(pOperator, operators.size() - 1);
            }
        }

        displayNumber();
        number = "";
        addNumber = false;
    }

    // Réinitialise toutes les données de la calculatrice
    private void reset() {
        number = "";
        numbers = new Vector();
        operators = new Vector();
        equation = "";
    }

    // Initialise tous les boutons de l'interface et ajoute le onClickListener à tous ces boutons
    private void initContentView() {
        txtResult = (TextView) findViewById(R.id.txtResultat);
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnMinus = (Button) findViewById(R.id.btnMinus);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        btnDivide = (Button) findViewById(R.id.btnDivide);
        btnMutipli = (Button) findViewById(R.id.btnMutipli);
        btnSigned = (Button) findViewById(R.id.btnSigned);
        btnSquare = (Button) findViewById(R.id.btnSquare);
        btnEquals = (Button) findViewById(R.id.btnEquals);
        btnComma = (Button) findViewById(R.id.btnComma);
        btnPourcent = (Button) findViewById(R.id.btnPourcent);
        btnFraction = (Button) findViewById(R.id.btnFraction);
        btnCE = (Button) findViewById(R.id.btnCE);
        btnC = (Button) findViewById(R.id.btnC);
        btnBack = (Button) findViewById(R.id.btnBack);

        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnDivide.setOnClickListener(this);
        btnMutipli.setOnClickListener(this);
        btnSigned.setOnClickListener(this);
        btnSquare.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
        btnComma.setOnClickListener(this);
        btnPourcent.setOnClickListener(this);
        btnFraction.setOnClickListener(this);
        btnCE.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    // Donne le bon format une String. Cette méthode sert principalement à l'affichage de la réponse sur le TextView
    private String formatToString(String pNumber) {
        if (pNumber.startsWith("0") && !pNumber.startsWith("0."))
            pNumber = pNumber.substring(1);
        if (pNumber.endsWith(".0"))
            pNumber = pNumber.substring(0, pNumber.length() - 2);
        return pNumber;
    }

    // Donne le bon format un nombre. Cette méthode sert principalement à l'affichage de la réponse sur le TextView
    private String formatToString(double pNumber) {
        String numberString = Double.toString(pNumber);
        return formatToString(numberString);
    }

    // Cette méthode permet de gérer toutes les actions permise sur l'interface.
    // Elle "dispatch" le traitement selon l'action faite par l'utilisateur
    private void operationsActions(View view) {
        double dNumber = 0;
        if (!number.equals("")) {
            dNumber = new Double(number).doubleValue();
        }
        switch (view.getId()) {
            case R.id.btnBack:
                if (number.length() > 0) {
                    number = number.substring(0, number.length() - 1);
                    displayNumber();
                } else {
                    addNumber = false;
                }
                break;
            case R.id.btnMinus:
                addOperator("-");
                break;
            case R.id.btnPlus:
                addOperator("+");
                break;
            case R.id.btnDivide:
                addOperator("÷");
                break;
            case R.id.btnMutipli:
                addOperator("*");
                break;
            case R.id.btnSigned:
                if (number != "") {
                    if (dNumber != 0)
                        dNumber *= -1;
                    number = formatToString(dNumber);
                    displayNumber();
                }
                break;
            case R.id.btnEquals:
                if (number != "")
                    numbers.add(number);
                if (operators.size() < numbers.size()) {
                    equation += getNumber();
                    calculateMultiDivide();
                    Double result = calculate();
                    displayResult(calculate());
                    number = formatToString(result);
                }
                break;
            case R.id.btnSquare:
                if (number != "") {
                    equation += "√" + getNumber();
                    numbers.add(Double.toString(Math.sqrt(dNumber)));
                    displayNumber();
                    number = "";
                    addNumber = false;
                }
                break;
            case R.id.btnPourcent:
                if (number != "") {
                    equation += getNumber() + "%";
                    double percentValue = dNumber / 100;
                    if (numbers.size() - 1 >= 0) {
                        double lastNumber = new Double(numbers.get(numbers.size() - 1)).doubleValue();
                        percentValue = (lastNumber * dNumber / 100);
                    }
                    numbers.add(Double.toString(percentValue));
                    displayNumber();
                    number = "";
                    addNumber = false;
                }
                break;
            case R.id.btnFraction:
                if (number != "") {
                    equation += "1/" + getNumber();
                    double fraction = 1 / dNumber;
                    numbers.add(formatToString(fraction));
                    displayNumber();
                    number = "";
                }
                break;
            case R.id.btn0:
                addNumber("0");
                break;
            case R.id.btn1:
                addNumber("1");
                break;
            case R.id.btn2:
                addNumber("2");
                break;
            case R.id.btn3:
                addNumber("3");
                break;
            case R.id.btn4:
                addNumber("4");
                break;
            case R.id.btn5:
                addNumber("5");
                break;
            case R.id.btn6:
                addNumber("6");
                break;
            case R.id.btn7:
                addNumber("7");
                break;
            case R.id.btn8:
                addNumber("8");
                break;
            case R.id.btn9:
                addNumber("9");
                break;
            case R.id.btnC:
                number = "";
                displayNumber();
                break;
            case R.id.btnCE:
                reset();
                displayNumber();
                break;
            case R.id.btnComma:
                addNumber = true;
                if (number == "")
                    number += "0." + number;

                displayNumber();
                break;
        }
    }

}
