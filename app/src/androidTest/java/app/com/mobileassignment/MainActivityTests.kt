package app.com.mobileassignment

import app.com.mobileassignment.uitests.*
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses

@RunWith(Suite::class)
@SuiteClasses(
        ActionTests::class,
        KeyboardTests::class,
        LaunchTests::class,
        ScrollTests::class,
        SearchTests::class
        )
class MainActivityTest

