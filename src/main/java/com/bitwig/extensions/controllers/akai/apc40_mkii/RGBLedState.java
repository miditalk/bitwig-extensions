package com.bitwig.extensions.controllers.akai.apc40_mkii;

import com.bitwig.extension.api.Color;
import com.bitwig.extension.controller.api.HardwareLightVisualState;
import com.bitwig.extension.controller.api.InternalHardwareLightState;

class RGBLedState extends InternalHardwareLightState
{
   private static final Color[] COLORS = new Color[128];

   public static final int COLOR_NONE = 0;

   public static final int COLOR_RED = 2;

   public static final int COLOR_GREEN = 18;

   public static final int COLOR_BLUE = 42;

   public static final int COLOR_YELLOW = 10;

   public static final int COLOR_RECORDING = COLOR_RED;

   public static final int COLOR_PLAYING = COLOR_GREEN;

   public static final int COLOR_PLAYING_QUEUED = COLOR_YELLOW;

   public static final int COLOR_STOPPING = COLOR_NONE;

   public static final int COLOR_SCENE = COLOR_YELLOW;

   public static final int COLOR_SELECTED = COLOR_YELLOW;

   public static final int COLOR_SELECTABLE = 1;

   public static final int BLINK_NONE = 0;

   public static final int BLINK_PLAY_QUEUED = 14;

   public static final int BLINK_ACTIVE = 10;

   public static final int BLINK_RECORD_QUEUED = 13;

   public static final int BLINK_STOP_QUEUED = 13;

   public static final RGBLedState OFF_STATE = new RGBLedState(COLOR_NONE, COLOR_NONE, BLINK_NONE);

   private static void registerColor(final int rgb, final int value)
   {

      assert value >= 0 && value <= 127;
      assert COLORS[value] == null;

      COLORS[value] = createColorForRGBInt(rgb);
   }

   private static Color createColorForRGBInt(final int rgb)
   {
      final int red = (rgb & 0xFF0000) >> 16;
      final int green = (rgb & 0xFF00) >> 8;
      final int blue = rgb & 0xFF;

      return Color.fromRGB255(red, green, blue);
   }

   private static double colorDistance(final Color color1, final Color color2)
   {
      final double r1 = color1.getRed();
      final double g1 = color1.getGreen();
      final double b1 = color1.getBlue();

      final double r2 = color2.getRed();
      final double g2 = color2.getGreen();
      final double b2 = color2.getBlue();

      final double dr = r2 - r1;
      final double dg = g2 - g1;
      final double db = b2 - b1;

      return Math.sqrt(dr * dr + dg * dg + db * db);
   }

   public static int getClosestColorIndex(final Color color)
   {

      if (color == null || color.getAlpha() == 0)
         return 0;

      int closestIndex = 0;
      double closestDistance = Double.MAX_VALUE;

      for (int i = 0; i < COLORS.length; i++)
      {
         final Color currentColor = COLORS[i];
         final double distance = colorDistance(color, currentColor);

         if (distance == 0)
            return i;

         if (distance < closestDistance)
         {
            closestIndex = i;
            closestDistance = distance;
         }
      }

      return closestIndex;
   }

   public static Color getColorForColorValue(final int colorValue)
   {
      assert colorValue >= 0 && colorValue < COLORS.length;

      if (colorValue < 0 || colorValue >= COLORS.length)
         return COLORS[0];

      return COLORS[colorValue];
   }

   public static RGBLedState getBestStateForColor(final Color color)
   {
      final int colorIndex = getClosestColorIndex(color);

      return new RGBLedState(colorIndex, COLOR_NONE, BLINK_NONE);
   }

   public RGBLedState(final int color, final int blinkColor, final int blinkType)
   {
      super();
      mColor = color;
      mBlinkColor = blinkColor;
      mBlinkType = blinkType;
   }

   public int getColor()
   {
      return mColor;
   }

   public int getBlinkColor()
   {
      return mBlinkColor;
   }

   public int getBlinkType()
   {
      return mBlinkType;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + mBlinkColor;
      result = prime * result + mBlinkType;
      result = prime * result + mColor;
      return result;
   }

   @Override
   public boolean equals(final Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      final RGBLedState other = (RGBLedState)obj;
      if (mBlinkColor != other.mBlinkColor)
         return false;
      if (mBlinkType != other.mBlinkType)
         return false;
      if (mColor != other.mColor)
         return false;
      return true;
   }

   @Override
   public HardwareLightVisualState getVisualState()
   {
      final Color color = getColorForColorValue(mColor);

      if (mBlinkType == BLINK_NONE)
         return HardwareLightVisualState.createForColor(color);

      final Color offColor = getColorForColorValue(mBlinkColor);

      if (mBlinkType == BLINK_PLAY_QUEUED)
         return HardwareLightVisualState.createBlinking(color, offColor, 0.2, 0.2);

      return HardwareLightVisualState.createBlinking(color, offColor, 0.5, 0.5);
   }

   private final int mColor, mBlinkColor, mBlinkType;

   static
   {
      registerColor(0x000000, 0);
      registerColor(0x1E1E1E, 1);
      registerColor(0x7F7F7F, 2);
      registerColor(0xFFFFFF, 3);
      registerColor(0xFF4C4C, 4);
      registerColor(0xFF0000, 5);
      registerColor(0x590000, 6);
      registerColor(0x190000, 7);
      registerColor(0xFFBD6C, 8);
      registerColor(0xFF5400, 9);
      registerColor(0x591D00, 10);
      registerColor(0x271B00, 11);
      registerColor(0xFFFF4C, 12);
      registerColor(0xFFFF00, 13);
      registerColor(0x595900, 14);
      registerColor(0x191900, 15);
      registerColor(0x88FF4C, 16);
      registerColor(0x54FF00, 17);
      registerColor(0x1D5900, 18);
      registerColor(0x142B00, 19);
      registerColor(0x4CFF4C, 20);
      registerColor(0x00FF00, 21);
      registerColor(0x005900, 22);
      registerColor(0x001900, 23);
      registerColor(0x4CFF5E, 24);
      registerColor(0x00FF19, 25);
      registerColor(0x00590D, 26);
      registerColor(0x001902, 27);
      registerColor(0x4CFF88, 28);
      registerColor(0x00FF55, 29);
      registerColor(0x00591D, 30);
      registerColor(0x001F12, 31);
      registerColor(0x4CFFB7, 32);
      registerColor(0x00FF99, 33);
      registerColor(0x005935, 34);
      registerColor(0x001912, 35);
      registerColor(0x4CC3FF, 36);
      registerColor(0x00A9FF, 37);
      registerColor(0x004152, 38);
      registerColor(0x001019, 39);
      registerColor(0x4C88FF, 40);
      registerColor(0x0055FF, 41);
      registerColor(0x001D59, 42);
      registerColor(0x000819, 43);
      registerColor(0x4C4CFF, 44);
      registerColor(0x0000FF, 45);
      registerColor(0x000059, 46);
      registerColor(0x000019, 47);
      registerColor(0x874CFF, 48);
      registerColor(0x5400FF, 49);
      registerColor(0x190064, 50);
      registerColor(0x0F0030, 51);
      registerColor(0xFF4CFF, 52);
      registerColor(0xFF00FF, 53);
      registerColor(0x590059, 54);
      registerColor(0x190019, 55);
      registerColor(0xFF4C87, 56);
      registerColor(0xFF0054, 57);
      registerColor(0x59001D, 58);
      registerColor(0x220013, 59);
      registerColor(0xFF1500, 60);
      registerColor(0x993500, 61);
      registerColor(0x795100, 62);
      registerColor(0x436400, 63);
      registerColor(0x033900, 64);
      registerColor(0x005735, 65);
      registerColor(0x00547F, 66);
      registerColor(0x0000FF, 67);
      registerColor(0x00454F, 68);
      registerColor(0x2500CC, 69);
      registerColor(0x7F7F7F, 70);
      registerColor(0x202020, 71);
      registerColor(0xFF0000, 72);
      registerColor(0xBDFF2D, 73);
      registerColor(0xAFED06, 74);
      registerColor(0x64FF09, 75);
      registerColor(0x108B00, 76);
      registerColor(0x00FF87, 77);
      registerColor(0x00A9FF, 78);
      registerColor(0x002AFF, 79);
      registerColor(0x3F00FF, 80);
      registerColor(0x7A00FF, 81);
      registerColor(0xB21A7D, 82);
      registerColor(0x402100, 83);
      registerColor(0xFF4A00, 84);
      registerColor(0x88E106, 85);
      registerColor(0x72FF15, 86);
      registerColor(0x00FF00, 87);
      registerColor(0x3BFF26, 88);
      registerColor(0x59FF71, 89);
      registerColor(0x38FFCC, 90);
      registerColor(0x5B8AFF, 91);
      registerColor(0x3151C6, 92);
      registerColor(0x877FE9, 93);
      registerColor(0xD31DFF, 94);
      registerColor(0xFF005D, 95);
      registerColor(0xFF7F00, 96);
      registerColor(0xB9B000, 97);
      registerColor(0x90FF00, 98);
      registerColor(0x835D07, 99);
      registerColor(0x392b00, 100);
      registerColor(0x144C10, 101);
      registerColor(0x0D5038, 102);
      registerColor(0x15152A, 103);
      registerColor(0x16205A, 104);
      registerColor(0x693C1C, 105);
      registerColor(0xA8000A, 106);
      registerColor(0xDE513D, 107);
      registerColor(0xD86A1C, 108);
      registerColor(0xFFE126, 109);
      registerColor(0x9EE12F, 110);
      registerColor(0x67B50F, 111);
      registerColor(0x1E1E30, 112);
      registerColor(0xDCFF6B, 113);
      registerColor(0x80FFBD, 114);
      registerColor(0x9A99FF, 115);
      registerColor(0x8E66FF, 116);
      registerColor(0x404040, 117);
      registerColor(0x757575, 118);
      registerColor(0xE0FFFF, 119);
      registerColor(0xA00000, 120);
      registerColor(0x350000, 121);
      registerColor(0x1AD000, 122);
      registerColor(0x074200, 123);
      registerColor(0xB9B000, 124);
      registerColor(0x3F3100, 125);
      registerColor(0xB35F00, 126);
      registerColor(0x4B1502, 127);
   }
}
