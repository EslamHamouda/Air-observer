  function setDial(aqi) {
    let angle = getAQIDialAngle(aqi);
    let [bg, white] = getAQIColor(aqi);
    let meter = document.querySelector(".gauge > div[role=meter]");
    let dial = meter.querySelector(".dial");
    meter.setAttribute("aria-valuenow", aqi);
    meter.setAttribute("aria-valuetext", aqi);
    dial.querySelector(".aqi-num").textContent = aqi;
    dial.querySelector(".arrow").style.transform = `rotate(${angle - 90}deg)`;
    dial.style.backgroundColor = bg;
    dial.classList.toggle("white", white);
  }


function getAQIDialAngle(aqi) {
  if (aqi >= 301) {
    return Math.min((aqi - 301) / 200 * 30 + 150, 180);
  } else if (aqi >= 201) {
    return (aqi - 201) / 100 * 30 + 120;
  } else if (aqi >= 151) {
    return (aqi - 151) / 50 * 30 + 90;
  } else if (aqi >= 101) {
    return (aqi - 101) / 50 * 30 + 60;
  } else if (aqi >= 51) {
    return (aqi - 51) / 50 * 30 + 30;
  } else if (aqi >= 0) {
    return aqi / 50 * 30;
  } else {
    return 0;
  }
}

function getAQIColor(aqi) {
  function combineColors(c1, c2, bias) {
    return c1.map((c, i) => ((c * (1 - bias)) + (c2[i] * bias)));
  }

  function stringifyColor(c) {
    return `rgb(${c})`;
  }

  function calculateColors(c1, c2, bias) {
    let bg = combineColors(c1, c2, bias);
    let white = ((bg[0] * 299) + (bg[1] * 587) + (bg[2] * 114)) / 1000 < 128;
    return [stringifyColor(bg), white];
  }

  const aqiColorMap = [
    [0, [0, 255, 0]],
    [50, [255, 255, 0]],
    [100, [255, 126, 0]],
    [150, [255, 0, 0]],
    [200, [143, 63, 151]],
    [300, [126, 0, 35]]
  ];

  for (let i in aqiColorMap) {
    let [target, color] = aqiColorMap[i];
    if (target > aqi) {
      if (i == 0) {
        return calculateColors(color, color, 1);
      }

      let [prevTarget, prevColor] = aqiColorMap[i - 1];
      return calculateColors(prevColor, color, (aqi - prevTarget) / (target - prevTarget));
    }
  }

  let [, color] = aqiColorMap[aqiColorMap.length - 1];
  return calculateColors(color, color, 1);
}

//setDial(100);