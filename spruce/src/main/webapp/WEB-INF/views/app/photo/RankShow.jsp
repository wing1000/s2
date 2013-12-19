`suppressNull on`
`import fengfei.ucm.entity.photo.*;import fengfei.fir.utils.Path;import java.util.*;import java.util.Map.Entry;`
`args Rank rank`

<div class="stats">
    <div class="column photo_stats_card photo_score_stats" title="&{pluse.alt}">
        <strong>${rank.sscore}</strong>

    </div>

    <div class="column photo_stats_card border-left">
        <ul>

            <li title="&{view.num}">
                <strong class="key">${rank.view}</strong>
                <small class="value">&{view}</small>
            </li>

            <li title="&{vote.num}">
                <strong
                        class="key">${rank.vote}</strong>
                <small class="value">&{view}</small>
            </li>

            <li title="&{favorite.num}">
                <strong
                        class="key">${rank.favorite}</strong>
                <small class="value">&{favorite}</small>
            </li>

        </ul>
    </div>


    <div class="column photo_stats_card border-left">
        <ul>
            <li><strong class="photo_max_stats">${rank.sMaxScore}</strong></li>
            <li>
                <small class="faded">&{"highest.pulse"}</small>
            </li>
            <li>
                <small class="faded key">${rank.sMaxAt}</small>
            </li>
        </ul>
    </div>
</div>