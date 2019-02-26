package com.example.flickrsearch.lithograph

import android.graphics.Color
import com.facebook.litho.Column
import com.facebook.litho.Component
import com.facebook.litho.ComponentContext
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Text
import com.facebook.yoga.YogaEdge


@LayoutSpec
object ListItemSpec {

    @JvmStatic
    @OnCreateLayout
    fun onCreateLayout(
        c: ComponentContext,
        @Prop color: Int,
        @Prop title: String,
        @Prop subTitle: String

    ): Component {
        return Column.create(c)
            .paddingDip(YogaEdge.ALL, 16f)
            .backgroundColor(color)
            .child(
                Text.create(c)
                    .text(title)
                    .textSizeSp(40f)
            )
            .child(
                Text.create(c)
                    .text(subTitle)
                    .textSizeSp(20f)
            )
            .build()
    }
}

