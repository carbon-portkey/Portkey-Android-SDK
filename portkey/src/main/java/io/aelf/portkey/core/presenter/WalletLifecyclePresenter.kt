package io.aelf.portkey.core.presenter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.aelf.portkey.behaviour.entry.EntryBehaviourEntity
import io.aelf.portkey.behaviour.guardian.GuardianBehaviourEntity
import io.aelf.portkey.behaviour.login.LoginBehaviourEntity
import io.aelf.portkey.behaviour.pin.SetPinBehaviourEntity
import io.aelf.portkey.behaviour.pin.WalletUnlockEntity
import io.aelf.portkey.behaviour.register.RegisterBehaviourEntity
import io.aelf.portkey.behaviour.wallet.PortkeyWallet
import io.aelf.portkey.core.stage.social_recovery.SocialRecoveryStageEnum
import io.aelf.portkey.internal.model.guardian.GuardianWrapper

internal object WalletLifecyclePresenter {

    internal var entry: EntryBehaviourEntity.CheckedEntry? by mutableStateOf(null)
    internal var login: LoginBehaviourEntity? by mutableStateOf(null)
    internal var register: RegisterBehaviourEntity? by mutableStateOf(null)
    internal var activeGuardian: GuardianBehaviourEntity? by mutableStateOf(null)
    internal var activeGuardians: List<GuardianWrapper> by mutableStateOf(emptyList())
    internal var setPin: SetPinBehaviourEntity? by mutableStateOf(null)
    internal var wallet: PortkeyWallet? by mutableStateOf(null)
    internal var unlock: WalletUnlockEntity? by mutableStateOf(null)

    internal var stageEnum by mutableStateOf(SocialRecoveryStageEnum.INIT)

    internal fun inferCurrentStage() {
        stageEnum =
            if (wallet != null) {
                SocialRecoveryStageEnum.ACTIVE
            } else if (unlock != null) {
                SocialRecoveryStageEnum.UNLOCK
            } else if (setPin != null) {
                SocialRecoveryStageEnum.SET_PIN
            } else if (register != null) {
                SocialRecoveryStageEnum.READY_TO_REGISTER
            } else if (login != null) {
                SocialRecoveryStageEnum.READY_TO_LOGIN
            } else {
                SocialRecoveryStageEnum.INIT
            }
    }

    internal object SpecialStageIdentifier {
        internal var CHOSE_TO_INPUT_EMAIL by mutableStateOf(false)

        internal fun reset() {
            CHOSE_TO_INPUT_EMAIL = false
        }
    }

    internal fun reset(saveWallet: Boolean = false) {
        entry = null
        login = null
        register = null
        activeGuardian = null
        activeGuardians = emptyList()
        setPin = null
        unlock = null
        if (!saveWallet) wallet = null
        SpecialStageIdentifier.reset()
        inferCurrentStage()
    }
}






