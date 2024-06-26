package com.example.afinal.di

import com.example.afinal.feature.acceptloan.AcceptLoanRouter
import com.example.afinal.feature.addresses.AddressesRouter
import com.example.afinal.feature.auth.AuthRouter
import com.example.afinal.feature.home.HomeRouter
import com.example.afinal.feature.homepage.HomePageRouter
import com.example.afinal.feature.loandetails.LoanDetailsRouter
import com.example.afinal.feature.menupage.MenuPageRouter
import com.example.afinal.feature.myloanspage.MyLoansPageRouter
import com.example.afinal.feature.onboarding.OnboardingRouter
import com.example.afinal.feature.rejectloan.RejectLoanRouter
import com.example.afinal.feature.requestloan.RequestLoanRouter
import com.example.afinal.feature.special.SpecialRouter
import com.example.afinal.feature.splash.SplashRouter
import com.example.afinal.feature.support.SupportRouter
import com.example.afinal.navigation.AcceptLoanRouterImpl
import com.example.afinal.navigation.AddressesRouterImpl
import com.example.afinal.navigation.AuthRouterImpl
import com.example.afinal.navigation.HomePageRouterImpl
import com.example.afinal.navigation.HomeRouterImpl
import com.example.afinal.navigation.LanguageRouterImpl
import com.example.afinal.navigation.LoanDetailsRouterImpl
import com.example.afinal.navigation.MenuPageRouterImpl
import com.example.afinal.navigation.MyLoansPageRouterImpl
import com.example.afinal.navigation.OnboardingRouterImpl
import com.example.afinal.navigation.RejectLoanRouterImpl
import com.example.afinal.navigation.RequestLoanRouterImpl
import com.example.afinal.navigation.SpecialRouterImpl
import com.example.afinal.navigation.SplashRouterImpl
import com.example.afinal.navigation.SupportRouterImpl
import com.example.afinal.shared.fragmentDependencies.LocalNavigationHolder
import com.exapmle.afinal.feature.language.LanguageRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class LocalRouter

@Module
interface NavigationModule {
    companion object {
        @Singleton
        @Provides
        fun provideCicerone() = Cicerone.create()

        @Provides
        @Singleton
        fun provideRouter(cicerone: Cicerone<Router>): Router {
            return cicerone.router
        }

        @Provides
        @Singleton
        @LocalRouter
        fun provideLocalRouter(localNavigationHolder: LocalNavigationHolder): Router =
            localNavigationHolder.router

        @Provides
        @Singleton
        fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
            return cicerone.getNavigatorHolder()
        }

        @Provides
        @Singleton
        fun provideLocalNavigationHolder(): LocalNavigationHolder = LocalNavigationHolder()
    }

    @Binds
    fun bindAuthRouterImpl(impl: AuthRouterImpl): AuthRouter

    @Binds
    fun bindOnboardingRouterImpl(impl: OnboardingRouterImpl): OnboardingRouter

    @Binds
    fun bindHomeRouterImpl(impl: HomeRouterImpl): HomeRouter

    @Binds
    fun bindLanguageRouterImpl(impl: LanguageRouterImpl): LanguageRouter

    @Binds
    fun bindSpecialRouterImpl(impl: SpecialRouterImpl): SpecialRouter

    @Binds
    fun bindSupportRouterImpl(impl: SupportRouterImpl): SupportRouter

    @Binds
    fun bindAddressesRouterImpl(impl: AddressesRouterImpl): AddressesRouter

    @Binds
    fun bindHomePageRouterImpl(impl: HomePageRouterImpl): HomePageRouter

    @Binds
    fun bindMenuPageRouterImpl(impl: MenuPageRouterImpl): MenuPageRouter

    @Binds
    fun bindMyLoansPageRouter(impl: MyLoansPageRouterImpl): MyLoansPageRouter

    @Binds
    fun bindLoanDetailsRouter(impl: LoanDetailsRouterImpl): LoanDetailsRouter

    @Binds
    fun bindRequestLoanRouter(impl: RequestLoanRouterImpl): RequestLoanRouter

    @Binds
    fun bindRejectLoanRouter(impl: RejectLoanRouterImpl): RejectLoanRouter

    @Binds
    fun bindAcceptLoanRouter(impl: AcceptLoanRouterImpl): AcceptLoanRouter

    @Binds
    fun bindSplashRouter(impl: SplashRouterImpl): SplashRouter

}