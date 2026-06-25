package com.cloudflare.realtimekit.flutter.view.livestream

import android.content.Context
import android.net.Uri
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout
import com.amazonaws.ivs.player.Player
import com.amazonaws.ivs.player.PlayerView
import io.dyte.flutter.R

class RtkLivestreamView(private val url: String?, private val context: Context) :
  FrameLayout(context) {
  private val mDyteLivestreamViewContainer: FrameLayout
  private val player: Player
  private val playerView: PlayerView

  init {
    inflate(context, R.layout.livestream_view, this)
    mDyteLivestreamViewContainer = findViewById(R.id.livestream_view)
    val surface = SurfaceView(context)
    playerView = PlayerView(context)
    player = playerView.player
    surface.holder.addCallback(object : SurfaceHolder.Callback {
      override fun surfaceCreated(holder: SurfaceHolder) {
        if (player != null) {
          player.setSurface(holder.surface)
        }
        url.let {
          player.load(Uri.parse(url))
          player.play()
        }
      }

      override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        player.setSurface(p0.surface)
        player.play()
      }

      override fun surfaceDestroyed(p0: SurfaceHolder) {
        if (player != null) {
          player.setSurface(null)
        }
      }
    })
    mDyteLivestreamViewContainer.addView(surface)
  }
}

