package dev.mvvasilev.taskmanager;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import dev.mvvasilev.taskmanager.ui.main.SectionsPagerAdapter;
import dev.mvvasilev.taskmanager.ui.main.fragment.CreateTaskFragment;
import dev.mvvasilev.taskmanager.ui.main.fragment.ListTasksFragment;
import dev.mvvasilev.taskmanager.ui.main.fragment.RemoveAdsFragment;

/**
 * Да се създаде дизайн за приложение „Организатор на задачи“.
 *
 * В главното Activity да има отгоре:
 *   бутон за преглед на всички незавършени задачи,
 *   бутон за добавяне на нова задача,
 *   бутон, водещ към Activity, което да позволява закупуване на платена версия на същото приложение, с премахнати реклами.
 *
 * Отдолу да има CalendarView с възможност за избор на ден.
 *
 * При избран ден и при кликване на бутон за добавяне на нова задача да се зарежда Activity за
 * добавяне на задача със следните атрибути:
 *   Име,
 *   начален час,
 *   краен час,
 *   приоритет (без приоритет, нисък, среден, висок),
 *   Нотификации (да/не) (показва дали да са включени нотификациите за тази задача),
 *   Описание на задачата.
 *
 * В главното Activity да има ImageView, което да представлява реклама,
 * с опция да се скрива след 30 секунди след зареждане на Activity-то.
 */
public class MainActivity extends AppCompatActivity implements ListTasksFragment.OnFragmentInteractionListener, CreateTaskFragment.OnFragmentInteractionListener, RemoveAdsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}