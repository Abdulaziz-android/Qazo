package uz.mnsh.qazo.presentation.main.qibla

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import uz.mnsh.qazo.R
import uz.mnsh.qazo.databinding.FragmentQiblaBinding
import uz.mnsh.qazo.presentation.main.MainActivity

class QiblaFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentQiblaBinding? = null
    private val binding get() = _binding!!
    private var mAzimuth: Int = 0
    private var rotation = 240
    private var mRotationV: Sensor? = null
    private var mAccelerometer: Sensor? = null
    private var mMagnetometer: Sensor? = null

    private val mLastAccelerometer = FloatArray(3)
    private val mLastMagnetometer = FloatArray(3)
    private var rMat = FloatArray(9)
    private var orientation = FloatArray(3)
    private var mLastAccelerometerSet = false
    private var mLastMagnetometerSet = false
    private lateinit var mSensorManager: SensorManager
    private var haveSensor = false
    private var haveSensor2 = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQiblaBinding.inflate(inflater, container, false)

        mSensorManager =
            (activity as MainActivity).getSystemService(Context.SENSOR_SERVICE) as SensorManager

        return binding.root
    }

    private fun start() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR) == null) {
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null || mSensorManager.getDefaultSensor(
                    Sensor.TYPE_MAGNETIC_FIELD
                ) == null
            ) {
                noSensorsAlert()
            } else {
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
                mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
                haveSensor = mSensorManager.registerListener(
                    this,
                    mAccelerometer,
                    SensorManager.SENSOR_DELAY_UI
                )
                haveSensor2 = mSensorManager.registerListener(
                    this,
                    mMagnetometer,
                    SensorManager.SENSOR_DELAY_UI
                )
            }
        } else {
            mRotationV = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
            haveSensor =
                mSensorManager.registerListener(this, mRotationV, SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun noSensorsAlert() {
        val alertDialog = AlertDialog.Builder(binding.root.context)
        alertDialog.setMessage("Your device doesn't support the Compass.")
            .setCancelable(false)
            .setNegativeButton(
                "Close"
            ) { dialog, id ->
                Navigation.findNavController(
                    activity as MainActivity,
                    R.id.nav_host_fragment_content_main
                ).popBackStack()
            }
        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onSensorChanged(event: SensorEvent) {

        if (rotation > 250 || rotation < 200) {
            rotation = 235
        }

        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rMat, event.values)
            mAzimuth = (Math.toDegrees(
                SensorManager.getOrientation(
                    rMat,
                    orientation
                )[0].toDouble()
            ) + 360).toInt() % 360
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.size)
            mLastAccelerometerSet = true
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.size)
            mLastMagnetometerSet = true
        }
        if (mLastAccelerometerSet && mLastMagnetometerSet) {
            SensorManager.getRotationMatrix(rMat, null, mLastAccelerometer, mLastMagnetometer)
            SensorManager.getOrientation(rMat, orientation)
            mAzimuth = (Math.toDegrees(
                SensorManager.getOrientation(
                    rMat,
                    orientation
                )[0].toDouble()
            ) + 360).toInt() % 360
        }

        mAzimuth = Math.round(mAzimuth.toFloat())
        binding.imgQibla.rotation = (-mAzimuth + rotation).toFloat()

        /*
            compass_img.setRotation(-mAzimuth+rotation);*/


        /*
            compass_img.setRotation(-mAzimuth+rotation);*/
        var where = "NW"

        if (mAzimuth >= 350 || mAzimuth <= 10) where = "N"
        if (mAzimuth in 281..349) where = "NW"
        if (mAzimuth in 261..280) where = "W"
        if (mAzimuth in 191..260) where = "SW"
        if (mAzimuth in 171..190) where = "S"
        if (mAzimuth in 101..170) where = "SE"
        if (mAzimuth in 81..100) where = "E"
        if (mAzimuth in 11..80) where = "NE"


        binding.azimuthTv.text = "$mAzimuth $where"
        // txt_compass.setText(mAzimuth.toString() + "° " + where)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }


    private fun stop() {
        if (haveSensor && haveSensor2) {
            mSensorManager.unregisterListener(this, mAccelerometer)
            mSensorManager.unregisterListener(this, mMagnetometer)
        } else {
            if (haveSensor) mSensorManager.unregisterListener(this, mRotationV)
        }
    }

    override fun onStart() {
        super.onStart()
        start()
    }

    override fun onStop() {
        super.onStop()
        stop()
    }
}