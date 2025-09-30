package tactician.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.util.Wiz;

public class DefendSFXAction extends AbstractGameAction {
	private final AbstractMonster m;

	public DefendSFXAction(AbstractMonster target) {
		this.target = target;
		this.m = target;
	}

	@Override
	public void update() {
		if ( Wiz.playerWeaponCalc(this.m, 9) > 0) { addToTop(new PlaySoundAction("tactician:Defend_Strong", 1.50f)); }
		else if ( Wiz.playerWeaponCalc(this.m, 9) < 0) { addToTop(new PlaySoundAction("tactician:Defend_Weak", 1.50f)); }
		else { addToTop(new PlaySoundAction("tactician:Defend_Neutral", 1.50f)); }
		this.isDone = true;
	} // See TacticianStrikeEffect for this implementation on Strike cards.
}