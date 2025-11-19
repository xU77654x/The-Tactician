package tactician.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import tactician.TacticianMod;

public class TutorialTactician extends FtueTip {
	public static final TutorialStrings tutorialStrings = CardCrawlGame.languagePack.getTutorialString("tactician:TutorialTactician");
	public static final String[] TEXT = tutorialStrings.TEXT;
	public static final String[] LABEL = tutorialStrings.LABEL;

	private final Color screen = Color.valueOf("1c262a00");
	private float x;
	private float scrollTimer = 0.0F; private float targetX; private float startX;
	private static final float SCROLL_TIME = 0.3F;
	private int currentSlot = 0;

	private static final TextureRegion imgp1 = new TextureRegion(new Texture(TacticianMod.imagePath("tutorial/Tactician1.png")));
	private static final TextureRegion imgp2 = new TextureRegion(new Texture(TacticianMod.imagePath("tutorial/Tactician2.png")));
	private static final TextureRegion imgp3 = new TextureRegion(new Texture(TacticianMod.imagePath("tutorial/Tactician3.png")));
	private static final TextureRegion imgp4 = new TextureRegion(new Texture(TacticianMod.imagePath("tutorial/Tactician3.png")));
	private static final String textp1 = TEXT[0];
	private static final String textp2 = TEXT[1];
	private static final String textp3 = TEXT[2];
	private static final String textp4 = TEXT[3];
	private static final String labelnext = LABEL[0];
	private static final String labelend = LABEL[1];
	private static final String labelpage = LABEL[2];
	private static final String labelpageend = LABEL[3];
	private static final String labeltitle = LABEL[4];
	private static final int tutorialPage = 4;

	public TutorialTactician() {
		AbstractDungeon.player.releaseCard();
		if (AbstractDungeon.isScreenUp) {
			AbstractDungeon.dynamicBanner.hide();
			AbstractDungeon.previousScreen = AbstractDungeon.screen;
		}
		AbstractDungeon.isScreenUp = true;
		AbstractDungeon.screen = AbstractDungeon.CurrentScreen.FTUE;
		AbstractDungeon.overlayMenu.showBlackScreen();
		this.x = 0.0F;
		AbstractDungeon.overlayMenu.proceedButton.show();
		CardCrawlGame.sound.playV("tactician:Zeal", 1.00F);
		AbstractDungeon.overlayMenu.proceedButton.setLabel(labelnext);
	}


	public void update() {
		if (this.screen.a != 0.8F) {
			this.screen.a += Gdx.graphics.getDeltaTime();
			if (this.screen.a > 0.8F) { this.screen.a = 0.8F; }
		}

		if ((AbstractDungeon.overlayMenu.proceedButton.isHovered && InputHelper.justClickedLeft) || CInputActionSet.proceed.isJustPressed()) {
			CInputActionSet.proceed.unpress();
			if (this.currentSlot == (-tutorialPage + 1)) {
				CardCrawlGame.sound.playV("tactician:Hex", 1.00F);
				AbstractDungeon.closeCurrentScreen();
				AbstractDungeon.overlayMenu.proceedButton.hide();
				AbstractDungeon.effectList.clear();
				AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
				return;
			}
			AbstractDungeon.overlayMenu.proceedButton.hideInstantly();
			AbstractDungeon.overlayMenu.proceedButton.show();
			CardCrawlGame.sound.playV("tactician:Anathema", 1.00F);
			this.currentSlot--;
			this.startX = this.x;
			this.targetX = (this.currentSlot * Settings.WIDTH);
			this.scrollTimer = 0.3F;
			if (this.currentSlot == (-tutorialPage + 1)) { AbstractDungeon.overlayMenu.proceedButton.setLabel(labelend); }
		}
		if (this.scrollTimer != 0.0F) {
			this.scrollTimer -= Gdx.graphics.getDeltaTime();
			if (this.scrollTimer < 0.0F) { this.scrollTimer = 0.0F; }
		}
		this.x = Interpolation.fade.apply(this.targetX, this.startX, this.scrollTimer / 0.3F);
	}


	public void render(SpriteBatch sb) {
		float x1 = 567.0F * Settings.scale;
		float x2 = x1 + Settings.WIDTH;
		float x3 = x2 + Settings.WIDTH;
		float x4 = x3 + Settings.WIDTH;
		sb.setColor(this.screen);
		sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);

		sb.setColor(Color.WHITE);
		sb.draw(imgp1, this.x + x1 - imgp1.getRegionWidth() / 2.0F, Settings.HEIGHT / 2.0F - imgp1.getRegionHeight() / 2.0F, imgp1.getRegionWidth() / 2.0F, imgp1.getRegionHeight() / 2.0F, imgp1.getRegionWidth(), imgp1.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
		sb.draw(imgp2, this.x + x2 - imgp2.getRegionWidth() / 2.0F, Settings.HEIGHT / 2.0F - imgp2.getRegionHeight() / 2.0F, imgp2.getRegionWidth() / 2.0F, imgp2.getRegionHeight() / 2.0F, imgp2.getRegionWidth(), imgp2.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
		sb.draw(imgp3, this.x + x3 - imgp3.getRegionWidth() / 2.0F, Settings.HEIGHT / 2.0F - imgp3.getRegionHeight() / 2.0F, imgp3.getRegionWidth() / 2.0F, imgp3.getRegionHeight() / 2.0F, imgp3.getRegionWidth(), imgp3.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);
		sb.draw(imgp4, this.x + x4 - imgp4.getRegionWidth() / 2.0F, Settings.HEIGHT / 2.0F - imgp4.getRegionHeight() / 2.0F, imgp4.getRegionWidth() / 2.0F, imgp4.getRegionHeight() / 2.0F, imgp4.getRegionWidth(), imgp4.getRegionHeight(), Settings.scale, Settings.scale, 0.0F);

		float offsetY = 0.0F;
		if (Settings.BIG_TEXT_MODE) { offsetY = 110.0F * Settings.scale; }

		FontHelper.renderSmartText(sb, FontHelper.panelNameFont, textp1, this.x + x1 + 350.0F * Settings.scale, Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, textp1, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F + offsetY, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
		FontHelper.renderSmartText(sb, FontHelper.panelNameFont, textp2, this.x + x2 + 480.0F * Settings.scale, Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, textp2, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F + offsetY, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
		FontHelper.renderSmartText(sb, FontHelper.panelNameFont, textp3, this.x + x3 + 350.0F * Settings.scale, Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, textp3, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F + offsetY, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
		FontHelper.renderSmartText(sb, FontHelper.panelNameFont, textp4, this.x + x4 + 350.0F * Settings.scale, Settings.HEIGHT / 2.0F - FontHelper.getSmartHeight(FontHelper.panelNameFont, textp4, 700.0F * Settings.scale, 40.0F * Settings.scale) / 2.0F + offsetY, 700.0F * Settings.scale, 40.0F * Settings.scale, Settings.CREAM_COLOR);
		FontHelper.renderFontCenteredWidth(sb, FontHelper.tipBodyFont, labelpage + Math.abs(this.currentSlot - 1) + labelpageend, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F - 460.0F * Settings.scale, Settings.CREAM_COLOR);
		FontHelper.renderFontCenteredWidth(sb, FontHelper.panelNameFont, labeltitle, Settings.WIDTH * 0.5F, Settings.HEIGHT / 2.0F - 420.0F * Settings.scale, Settings.GOLD_COLOR);
		AbstractDungeon.overlayMenu.proceedButton.render(sb);
	}
}