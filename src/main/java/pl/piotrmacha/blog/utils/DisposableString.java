package pl.piotrmacha.blog.utils;

import java.util.Arrays;

final public class DisposableString {
    private char[] contents;

    public DisposableString(char[] contents) {
        this.contents = contents;
    }

    public char[] chars() {
        return contents;
    }

    public void dispose() {
        Arrays.fill(contents, '\0');
    }
}
