package com.pakollya.exchangerates.base.presentation

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.pakollya.exchangerates.R

class CustomButton : androidx.appcompat.widget.AppCompatButton, BaseView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun enable(enabled: Boolean) {
        isEnabled = enabled
    }

    override fun show(textId: Int) {
        setText(textId)
    }

    override fun show(text: CharSequence) {
        setText(text)
    }

    override fun handleClick(listener: OnClickListener) {
        setOnClickListener(listener)
    }
}

class CustomTextView : androidx.appcompat.widget.AppCompatTextView, BaseView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun show(text: CharSequence) {
        setText(text)
    }

    override fun show(textId: Int) {
        setText(textId)
    }
}

class CustomImageView : androidx.appcompat.widget.AppCompatImageView, BaseView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun showImageResource(id: Int) {
        setImageResource(id)
    }

    override fun select(selected: Boolean) {
        visibility = if (selected) VISIBLE else INVISIBLE
    }
}

class FavoriteImageView : androidx.appcompat.widget.AppCompatImageView, BaseView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun showImageResource(id: Int) {
        setImageResource(id)
    }

    override fun select(selected: Boolean) = setImageResource(
        if (selected) R.drawable.ic_favorite else R.drawable.ic_not_favorite
    )
}

class CustomFrameLayout : FrameLayout, BaseView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun handleClick(listener: OnClickListener) {
        setOnClickListener(listener)
    }
}

class CustomLinearLayout : LinearLayout, BaseView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun handleClick(listener: OnClickListener) {
        setOnClickListener(listener)
    }

    override fun select(selected: Boolean) {
        setBackgroundColor(
            ContextCompat.getColor(
                this.context,
                if (selected) R.color.gray else R.color.white
            )
        )
    }
}

class CustomConstraintLayout : ConstraintLayout, BaseView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun handleClick(listener: OnClickListener) {
        setOnClickListener(listener)
    }
}