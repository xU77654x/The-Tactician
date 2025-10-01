package tactician.events;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.colorless.Apparition;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.city.Ghosts;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.List;
import static tactician.TacticianMod.makeID;

public class GhostsTactician extends AbstractImageEvent {
	public static final String ID = makeID(GhostsTactician.class.getSimpleName());
	public static final String NAME = CardCrawlGame.languagePack.getEventString(Ghosts.ID).NAME;
	private static final EventStrings custom_ES = CardCrawlGame.languagePack.getEventString(ID);
	private static final EventStrings vanilla_ES = CardCrawlGame.languagePack.getEventString(Ghosts.ID);

	private int screenNum = 0;
	private int hpLoss;
	private int hpGain = 10;

	public GhostsTactician() {
		super(NAME, vanilla_ES.DESCRIPTIONS[0], "images/events/ghost.jpg");

		this.hpLoss = MathUtils.ceil(AbstractDungeon.player.maxHealth * 0.5F);
		if (this.hpLoss >= AbstractDungeon.player.maxHealth) { this.hpLoss = AbstractDungeon.player.maxHealth - 1; }
		if (AbstractDungeon.ascensionLevel >= 15) { this.hpGain = 7; }
		this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[3] + this.hpLoss + vanilla_ES.OPTIONS[1], new Apparition());
		this.imageEventText.setDialogOption(custom_ES.OPTIONS[0] + this.hpGain + custom_ES.OPTIONS[1]);
		this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[2]);
	}

	public void onEnterRoom() { if (Settings.AMBIANCE_ON) { CardCrawlGame.sound.play("EVENT_GHOSTS"); }}

	protected void buttonEffect(int buttonPressed) {
		switch (this.screenNum) {
			case 0:
				switch (buttonPressed) {
					case 0:
						this.imageEventText.updateBodyText(vanilla_ES.DESCRIPTIONS[2]);
						AbstractDungeon.player.decreaseMaxHealth(this.hpLoss);
						becomeGhost();
						this.screenNum = 1;
						this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[5]);
						this.imageEventText.clearRemainingOptions();
						return;
					case 1:
						this.imageEventText.updateBodyText(custom_ES.DESCRIPTIONS[0]);
						AbstractDungeon.player.increaseMaxHp(hpGain, false);
						if (!AbstractDungeon.player.hasRelic(BlueCandle.ID)) {
							BlueCandle candle = new BlueCandle();
							AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, candle);
							logMetricObtainRelic("Ghosts", "Blue Candle", candle);
						}
						else {
							Circlet worthless = new Circlet();
							AbstractDungeon.getCurrRoom().spawnRelicAndObtain(this.drawX, this.drawY, worthless);
							logMetricObtainRelic("Ghosts", "BlueCandle", worthless);
						}
						this.screenNum = 1;
						this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[5]);
						this.imageEventText.clearRemainingOptions();
						return;
				}
				logMetricIgnored("Ghosts");
				this.imageEventText.updateBodyText(vanilla_ES.DESCRIPTIONS[3]);
				this.screenNum = 2;
				this.imageEventText.updateDialogOption(0, vanilla_ES.OPTIONS[5]);
				this.imageEventText.clearRemainingOptions();
				return;
			case 1:
				openMap();
				return;
		}
		openMap();
	}

	private void becomeGhost() {
		List<String> cards = new ArrayList<>();
		int amount = 5;
		if (AbstractDungeon.ascensionLevel >= 15) { amount -= 2; }
		for (int i = 0; i < amount; i++) {
			Apparition apparition = new Apparition();
			cards.add((apparition).cardID);
			AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(apparition, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
		}
		logMetricObtainCardsLoseMapHP("Ghosts", "Became a Ghost", cards, this.hpLoss);
	}
}