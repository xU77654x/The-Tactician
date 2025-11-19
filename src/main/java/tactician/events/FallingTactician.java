package tactician.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.beyond.Falling;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import tactician.cards.common.Elwind;
import static tactician.TacticianMod.makeID;

public class FallingTactician extends AbstractImageEvent {
	public static final String ID = makeID(FallingTactician.class.getSimpleName());
	public static final String NAME = CardCrawlGame.languagePack.getEventString(Falling.ID).NAME;
	private static final EventStrings custom_ES = CardCrawlGame.languagePack.getEventString(ID);
	private static final EventStrings vanilla_ES = CardCrawlGame.languagePack.getEventString(Falling.ID);

	private boolean attack; private boolean skill; private boolean power; private boolean elwind;
	private AbstractCard attackCard; private AbstractCard skillCard; private AbstractCard powerCard;
	private CurScreen screen = CurScreen.INTRO;
	private enum CurScreen { INTRO, CHOICE, RESULT }
	private static final String DIALOG_1 = vanilla_ES.DESCRIPTIONS[0];
	private static final String DIALOG_2 = vanilla_ES.DESCRIPTIONS[1];

	public FallingTactician() {
		super(NAME, vanilla_ES.DESCRIPTIONS[0], "images/events/falling.jpg");
		setCards();
		this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[0]);
	}

	public void onEnterRoom() { if (Settings.AMBIANCE_ON) { CardCrawlGame.sound.playV("EVENT_FALLING", 1.00F); }}

	private void setCards() {
		this.attack = CardHelper.hasCardWithType(AbstractCard.CardType.ATTACK);
		this.skill = CardHelper.hasCardWithType(AbstractCard.CardType.SKILL);
		this.power = CardHelper.hasCardWithType(AbstractCard.CardType.POWER);
		this.elwind = CardHelper.hasCardWithID(Elwind.ID);

		if (this.attack) { this.attackCard = CardHelper.returnCardOfType(AbstractCard.CardType.ATTACK, AbstractDungeon.miscRng); }
		if (this.skill) { this.skillCard = CardHelper.returnCardOfType(AbstractCard.CardType.SKILL, AbstractDungeon.miscRng); }
		if (this.power) { this.powerCard = CardHelper.returnCardOfType(AbstractCard.CardType.POWER, AbstractDungeon.miscRng); }
	}

	protected void buttonEffect(int buttonPressed) {
		switch (this.screen) {
			case INTRO:
				// switch (buttonPressed) {
					// case 0:
						this.screen = CurScreen.CHOICE;
						this.imageEventText.updateBodyText(DIALOG_2);
						this.imageEventText.clearAllDialogs();
						if (!this.skill && !this.power && !this.attack) { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[8]); }
						else {
							if (this.skill) { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[1] + FontHelper.colorString(this.skillCard.name, "r"), this.skillCard.makeStatEquivalentCopy()); }
							else { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[2], true); }
							if (this.power) { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[3] + FontHelper.colorString(this.powerCard.name, "r"), this.powerCard.makeStatEquivalentCopy()); }
							else { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[4], true); }
							if (this.attack) { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[5] + FontHelper.colorString(this.attackCard.name, "r"), this.attackCard.makeStatEquivalentCopy()); }
							else { this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[6], true); }
							if (this.elwind) { this.imageEventText.setDialogOption(custom_ES.OPTIONS[0]); }
							else { this.imageEventText.setDialogOption(custom_ES.OPTIONS[1], true); }
						}
						// break;
					// case 1:
						// break;
					// }
				return;

			case CHOICE:
				this.screen = CurScreen.RESULT;
				this.imageEventText.clearAllDialogs();
				this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[7]);
				switch (buttonPressed) {
					case 0:
						if (!this.skill && !this.power && !this.attack) {
							this.imageEventText.updateBodyText(vanilla_ES.DESCRIPTIONS[5]);
							logMetricIgnored("Falling"); break;
						}
						this.imageEventText.updateBodyText(vanilla_ES.DESCRIPTIONS[2]);
						AbstractDungeon.effectList.add(new PurgeCardEffect(this.skillCard));
						AbstractDungeon.player.masterDeck.removeCard(this.skillCard);
						logMetricCardRemoval("Falling", "Removed Skill", this.skillCard);
						break;
					case 1:
						this.imageEventText.updateBodyText(vanilla_ES.DESCRIPTIONS[3]);
						AbstractDungeon.effectList.add(new PurgeCardEffect(this.powerCard));
						AbstractDungeon.player.masterDeck.removeCard(this.powerCard);
						logMetricCardRemoval("Falling", "Removed Power", this.powerCard);
						break;
					case 2:
						this.imageEventText.updateBodyText(vanilla_ES.DESCRIPTIONS[4]);
						AbstractDungeon.effectList.add(new PurgeCardEffect(this.attackCard));
						logMetricCardRemoval("Falling", "Removed Attack", this.attackCard);
						AbstractDungeon.player.masterDeck.removeCard(this.attackCard);
						break;
					case 3:
						CardCrawlGame.sound.playV("tactician:Elwind", 1.67f);
						this.screen = CurScreen.RESULT;
						this.imageEventText.clearAllDialogs();
						this.imageEventText.updateBodyText(custom_ES.DESCRIPTIONS[0]);
						this.imageEventText.setDialogOption(vanilla_ES.OPTIONS[7]);
						break;
				}
			return;
		}
		openMap();
	}
}