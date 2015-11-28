package net.petitviolet.android_scaex;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.petitviolet.scaex.IF;
import net.petitviolet.scaex.Match;

public class MainActivity extends AppCompatActivity {
    private EditText mEditText;
    private Button mButtonIF;
    private Button mButtonMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEditText = (EditText) findViewById(R.id.edit_text);
        mButtonIF = (Button) findViewById(R.id.ifx);
        mButtonMatch = (Button) findViewById(R.id.match);
        mButtonIF.setOnClickListener(v -> testIFx(mEditText.getText().toString()));
        mButtonMatch.setOnClickListener(v -> testMatch(mEditText.getText().toString()));
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

    private void toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
