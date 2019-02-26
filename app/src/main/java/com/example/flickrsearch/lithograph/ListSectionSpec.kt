package com.example.flickrsearch.lithograph

import android.graphics.Color
import com.facebook.litho.sections.Children
import com.facebook.litho.sections.SectionContext
import com.facebook.litho.sections.annotations.GroupSectionSpec
import com.facebook.litho.sections.annotations.OnCreateChildren
import com.facebook.litho.sections.common.SingleComponentSection


@GroupSectionSpec
object ListSectionSpec {

    @OnCreateChildren
    fun onCreateChildren(c: SectionContext) : Children {

        val builder = Children.create()

        (0 until 32).forEach { i ->

            builder.child(
                SingleComponentSection.create(c)
                    .key(i.toString())
                    .component(
                        ListItem.create(c)
                            .color(if(i % 2 == 0) Color.WHITE else Color.LTGRAY)
                            .title("$i. Hello, world!")
                            .subTitle("Litho tutorial")
                            .build()
                    )
            )
        }

        return builder.build()
    }
}

