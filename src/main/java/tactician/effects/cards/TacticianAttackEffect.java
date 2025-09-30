package tactician.effects.cards;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import tactician.actions.PlaySoundAction;

public class TacticianAttackEffect extends AbstractGameEffect {
	private float x;
	private float y;
	private AbstractMonster m;
	private String soundKey;
	private float volume = 1.00F;

	public TacticianAttackEffect(AbstractMonster m, float x, float y, String soundKey, float volume) {
		this.x = x;
		this.y = y;
		this.m = m;
		this.soundKey = soundKey;
		this.volume = volume;
		this.startingDuration = 0.0F;
	}

	public void update() {
		AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(this.x, this.y, 0.0F, 0.0F, 315.0F, this.color, com.badlogic.gdx.graphics.Color.LIGHT_GRAY));
		AbstractDungeon.actionManager.addToTop(new PlaySoundAction(soundKey, volume));
		this.isDone = true;
	}

	public void render(SpriteBatch sb) {}

	public void dispose() {}
}