package com.mainstreetcode.teammate.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.mainstreetcode.teammate.model.Role;
import com.mainstreetcode.teammate.model.Team;
import com.mainstreetcode.teammate.model.User;
import com.mainstreetcode.teammate.repository.RoleRepository;

import io.reactivex.Completable;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;

/**
 * ViewModel for checking a role in local contexts
 */

public class LocalRoleViewModel extends ViewModel {

    private Role role = Role.empty();

    private final RoleRepository repository;

    public LocalRoleViewModel() {
        repository = RoleRepository.getInstance();
    }

    public boolean hasPrivilegedRole() {
        return role.isPrivilegedRole();
    }

    public Completable getRoleInTeam(User user, Team team) {
        return repository.getRoleInTeam(user.getId(), team.getId())
                .map(this::onRoleFound).observeOn(mainThread()).flatMapCompletable(sameRole -> Completable.complete());
    }

    private Role onRoleFound(Role foundRole) {
        role.update(foundRole);
        return role;
    }
}
