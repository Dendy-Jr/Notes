package com.olehvynnytskyi.notes.presentation

import android.app.Application
import com.olehvynnytskyi.notes.R
import com.olehvynnytskyi.notes.core.extension.getColorFromAttr
import com.olehvynnytskyi.notes.domain.model.Note
import com.olehvynnytskyi.notes.presentation.util.DateFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockNotesList @Inject constructor(
    private val application: Application,
    private val dateFormatter: DateFormatter,
) {

    // It doesn't work
    private val colors = listOf(
        application.getColorFromAttr(R.attr.colorWhite),
        application.getColorFromAttr(R.attr.colorRed),
        application.getColorFromAttr(R.attr.colorBlue),
        application.getColorFromAttr(R.attr.colorGreen),
        application.getColorFromAttr(R.attr.colorPink),
        application.getColorFromAttr(R.attr.colorYellow),
        application.getColorFromAttr(R.attr.colorOrange),
    )

    val notes = listOf(
        Note(
            id = 1,
            title = "Email",
            content = "These can be used to directly communicate updates to customers instead of waiting for them to use your product. They’re often used to celebrate exciting updates or announce important changes to the status quo. ",
            date = dateFormatter.format(System.currentTimeMillis()),
            color = colors.random()
        ), Note(
            id = 2,
            title = "Blog posts",
            content = "These are designed to handle longer-form content than a standard email or in-app message. This makes them a perfect format for releases that deserve more copy or explanation.",
            date = dateFormatter.format(System.currentTimeMillis()),
            color = colors.random()
        ), Note(
            id = 3,
            title = "Social media",
            content = "This is a great way to spotlight single features and build hype for new releases and updates.",
            date = dateFormatter.format(System.currentTimeMillis()),
            color = colors.random()
        ), Note(
            id = 4,
            title = "App Store/Google Play Store",
            content = "Release notes are common in Apple’s App Store and the Google Play Store. For example, users looking to buy or update a web or mobile app in the App Store can view an extensive list of release notes when selecting a product. The TikTok app version history looks like this:",
            date = dateFormatter.format(System.currentTimeMillis()),
            color = colors.random()
        ), Note(
            id = 5,
            title = "Make the intent of the changes clear",
            content = "The people who scan release notes are there for a reason—they want information on what's been changed and how that affects them.  Make sure the text clearly shows what’s been changed, and highlight what those changes mean to the user. Also, don’t be afraid to use some humor to put the user at ease.",
            date = dateFormatter.format(System.currentTimeMillis()),
            color = colors.random()
        ), Note(
            id = 6,
            title = "Focus release notes on the user",
            content = "Remember, release notes are a great line of communication with your customer. Empathize with your user—assume their role in seeking information. Understand them, know them, learn about them. When compiling your notes, ask what are they looking for? Can they easily find it? ",
            date = dateFormatter.format(System.currentTimeMillis()),
            color = colors.random()
        ), Note(
            id = 7,
            title = "Explain changes with visuals",
            content = "Most consumers only remember about 20% of the text they read when it's not accompanied by visuals, and according to one eLearning website, “ well-selected images can improve comprehension and insight when developers strategically place such graphics.” If your release notes need a lengthy explanation, include a short GIF or a helpful screenshot that shows exactly what that change does. ",
            date = dateFormatter.format(System.currentTimeMillis()),
            color = colors.random()
        )
    )
}