-keep public class * {
    public protected <fields>;
    public protected <methods>;
}

-assumenosideeffects class android.util.Log {
    public static *** d(...);
}