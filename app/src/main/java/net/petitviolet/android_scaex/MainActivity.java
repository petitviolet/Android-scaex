package net.petitviolet.android_scaex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.petitviolet.scaex.For;
import net.petitviolet.scaex.IF;
import net.petitviolet.scaex.Match;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private Button mButtonIF;
    private Button mButtonMatch;
    private Button mButtonFor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mButtonIF = (Button) findViewById(R.id.ifx);
        mButtonMatch = (Button) findViewById(R.id.match);
        mButtonFor = (Button) findViewById(R.id.forx);
        mButtonIF.setOnClickListener(v -> testIFx(mEditText.getText().toString()));
        mButtonMatch.setOnClickListener(v -> testMatch(mEditText.getText().toString()));
        mButtonFor.setOnClickListener(v -> testFor(mEditText.getText().toString()));
    }

    private void testMatch(String input) {
        String result = Match.<String, String>x(input)
                .Case(TextUtils::isEmpty).then("Empty match")
                .Case((s) -> s.length() < 3).then("short string match")
                .eval("long string match");
        toast(result);
    }
    private void testIFx(String input) {
        String result = IF.<String>x(input.isEmpty()).then("Empty ifx")
                .ElseIf(input.length() < 3).then("short string ifx")
                .Else("long string ifx");
        toast(result);
    }

    private void testFor(String input) {
        List<String> inputs = Arrays.asList(
                mButtonIF.toString(),
                mButtonFor.toString(),
                mButtonMatch.toString());
        For.x(inputs).action(s -> {
            Log.i(String.format("testFor: %s", input), s);
        });

        For.x(1).to(10).by(3).action(i -> Log.i(String.format("testFor: %s", input), "i: " + i));

        List<String> results = For.x(inputs).apply(s -> String.format("applied: %s: %s", input, s));

        toast(Arrays.toString(results.toArray()));
    }
    private void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
