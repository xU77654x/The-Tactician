package tactician.util;
import basemod.TopPanelItem;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import tactician.TacticianMod;
import static tactician.TacticianMod.characterPath;
import static tactician.character.TacticianRobin.Meta.TACTICIAN;

public class WeaponTypeChart extends TopPanelItem {
	public static final String ID = TacticianMod.makeID(WeaponTypeChart.class.getSimpleName());
	public static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("tactician:WeaponTriangleChart");
	public static final String[] TEXT = uiStrings.TEXT;
	public static boolean display = false;
	public static boolean clicked = false;
	private static final Texture weaponButton = TextureLoader.getTexture(characterPath("Tactician_WeaponTriangleButton.png"));
	private static final Texture weaponChart = TextureLoader.getTexture(characterPath("Tactician_WeaponTriangleChart.png"));

	public WeaponTypeChart() { super(weaponButton, ID); }

	public boolean isClickable() { return (!AbstractDungeon.isScreenUp || display); }

	public void render(SpriteBatch sb) {
		if (AbstractDungeon.player.chosenClass == TACTICIAN) {
			super.render(sb); // Hard-coded for this image, which is 530p x 320p. The top bar is 65 pixels wide.
			if (display) { sb.draw(weaponChart, (float) (Settings.WIDTH / 2) - 265, (float) (Settings.HEIGHT) - (320 + 65)); }
		}
	}

	@Override
	protected void onClick() {
		clicked = !clicked;
		if (display) { display = false; }
		else { if (isClickable()) { display = true; }}
	}

	@Override
	protected void onHover() {
		if (isClickable()) { display = true; }
		TipHelper.renderGenericTip(this.x - weaponButton.getWidth(), this.y - weaponButton.getHeight(), TEXT[0], TEXT[1]);
	}

	@Override
	protected void onUnhover() { if (!clicked) { display = false;} }
} // Credit to Packmaster and Urshifu for the code.