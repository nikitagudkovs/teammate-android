package com.mainstreetcode.teammate.adapters;

import android.view.ViewGroup;

import com.mainstreetcode.teammate.R;
import com.mainstreetcode.teammate.adapters.viewholders.BaseItemViewHolder;
import com.mainstreetcode.teammate.adapters.viewholders.ClickInputViewHolder;
import com.mainstreetcode.teammate.adapters.viewholders.InputViewHolder;
import com.mainstreetcode.teammate.adapters.viewholders.RoleSelectViewHolder;
import com.mainstreetcode.teammate.fragments.headless.ImageWorkerFragment;
import com.mainstreetcode.teammate.model.Item;
import com.mainstreetcode.teammate.model.Team;
import com.tunjid.androidbootstrap.core.abstractclasses.BaseRecyclerViewAdapter;

import java.util.List;

import static com.mainstreetcode.teammate.util.ViewHolderUtil.getItemView;

/**
 * Adapter for {@link Team}
 */

public class TeamEditAdapter extends BaseRecyclerViewAdapter<BaseItemViewHolder, TeamEditAdapter.TeamEditAdapterListener> {


    private final Team team;
    private final List<String> roles;

    public TeamEditAdapter(Team team, List<String> roles, TeamEditAdapter.TeamEditAdapterListener listener) {
        super(listener);
        this.team = team;
        this.roles = roles;
    }

    @Override
    public BaseItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case Item.INFO:
                return new InputViewHolder(getItemView(R.layout.viewholder_simple_input, viewGroup), () -> false);
            case Item.INPUT:
                return new InputViewHolder(getItemView(R.layout.viewholder_simple_input, viewGroup), adapterListener::isPrivileged);
            case Item.ROLE:
                return new RoleSelectViewHolder(getItemView(R.layout.viewholder_simple_input, viewGroup), roles, adapterListener::isJoiningTeam);
            case Item.CITY:
            case Item.STATE:
                return new ClickInputViewHolder(getItemView(R.layout.viewholder_simple_input, viewGroup), adapterListener::isPrivileged, adapterListener::onAddressClicked);
            case Item.ZIP:
                return new ClickInputViewHolder(getItemView(R.layout.viewholder_simple_input, viewGroup), adapterListener::isPrivileged, adapterListener::onAddressClicked, () -> false);
            default:
                return new BaseItemViewHolder(getItemView(R.layout.viewholder_simple_input, viewGroup));
        }
    }

    @Override
    public void onBindViewHolder(BaseItemViewHolder baseTeamViewHolder, int i) {
        baseTeamViewHolder.bind(team.get(i));
    }

    @Override
    public int getItemCount() {
        return adapterListener.isJoiningTeam() ? team.size() : team.size() - 1;
    }

    @Override
    public int getItemViewType(int position) {
        return team.get(position).getItemType();
    }

    public interface TeamEditAdapterListener extends ImageWorkerFragment.ImagePickerListener {
        void onAddressClicked();

        boolean isJoiningTeam();

        boolean isPrivileged();
    }
}
